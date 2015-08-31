package dao.ebean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import dao.EnvironmentDAO;
import play.Logger;
import play.db.DB;
import models.Environment;
import models.EnvironmentType;
import models.LocalizationType;
import models.User;

public class EnvironmentEbeanDAO extends BaseEbeanDAO<Environment, Integer> implements EnvironmentDAO {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private Connection connection;

	@Override
	public int createAll(List<Environment> entities) {
		for (Environment e : entities) {
			create(e);
		}
		return 0;
	}
	
	public List<Environment> findAll() {
		return findAll(null, null);
	}
	
	public List<Environment> findAll(Integer limit) {
		return findAll(limit, null);
	}
	
	public List<Environment> findAll(Integer limit, Integer offset) {
		List<Environment> result = new ArrayList<Environment>();
		
		String sql = "SELECT e.id, e.name, e.location, e.shape, e.operating_range, e.version, e.parent_environment_id, e.level, "
				   + "et.id as et_id, et.name as et_name, lt.id as lt_id, lt.name as lt_name, "
				   + "lt.precision as lt_precision, lt.metric as lt_metric "
				   + "FROM environments e "
				   + "INNER JOIN environment_types AS et ON et.id = e.environment_type_id "
				   + "INNER JOIN localization_types AS lt ON lt.id = e.localization_type_id";
		
		if (limit != null) sql += " LIMIT ?";
		if (offset != null) sql += " OFFSET ?";
		
		try {
			PreparedStatement s = getConnection().prepareStatement(sql);
			
			if (limit != null) s.setInt(1, limit);
			if (offset != null) s.setInt(2, offset);
			
	        ResultSet r = s.executeQuery();

	        while(r.next()) {
	        	result.add(parseResultSet(r));
	        }
	        
	        r.close();
	        s.close();
	        
	        return result;
		} catch (SQLException ex) {
			logger.error("Error while inserting environment.", ex);
			return null;
		}
	}
	
	public List<Environment> findNearBy(Point location, double radius) {
		List<Environment> result = new ArrayList<Environment>();
		
		String sql = "SELECT e.id, e.name, e.location, e.shape, e.operating_range, e.version, e.parent_environment_id, e.level, "
				   + "et.id as et_id, et.name as et_name, lt.id as lt_id, lt.name as lt_name, "
				   + "lt.precision as lt_precision, lt.metric as lt_metric, "
				   + "ST_Distance_Sphere(e.location, ST_SetSRID(ST_MakePoint(?, ?, ?), 4326)) as distance_meters "
				   + "FROM environments e "
				   + "INNER JOIN environment_types AS et ON et.id = e.environment_type_id "
				   + "INNER JOIN localization_types AS lt ON lt.id = e.localization_type_id "
				   + "WHERE ST_DWithin(Geography(e.location), ST_SetSRID(ST_MakePoint(?, ?, ?), 4326), ?) "
				   + "ORDER BY distance_meters;";
		
		try {
			PreparedStatement s = getConnection().prepareStatement(sql);
			s.setDouble(1, location.getX());
			s.setDouble(2, location.getY());
			s.setDouble(3, location.getZ());
			s.setDouble(4, location.getX());
			s.setDouble(5, location.getY());
			s.setDouble(6, location.getZ());
			s.setDouble(7, radius);
			
	        ResultSet r = s.executeQuery();

	        while(r.next()) {
	        	result.add(parseResultSet(r));
	        }
	        
	        r.close();
	        s.close();
	        
	        return result;
		} catch (SQLException ex) {
			logger.error("Error while inserting environment.", ex);
			return null;
		}
	}

	@Override
	public Environment create(Environment entity) {
		String sql = "INSERT INTO environments (name, location, shape, operating_range, "
				   + "version, parent_environment_id, environment_type_id, localization_type_id, level) "
				   + "VALUES (?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement s = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, entity.getName());
	        s.setObject(2, new PGgeometry(entity.getLocation()));
	        s.setDouble(4, entity.getOperatingRange());
	        s.setInt(5, entity.getVersion());

	        s.setInt(7, entity.getEnvironmentType().getId());
	        s.setInt(8, entity.getLocalizationType().getId());
	        s.setInt(9, entity.getLevel());

			if (entity.getParent() != null) {
				s.setInt(6, entity.getParent().getId());
			} else {
				s.setNull(6, Types.NULL);
			}
	        
	        if (entity.getShape() != null) {
	        	s.setObject(3, new PGgeometry(entity.getShape()));
	        } else {
	        	s.setNull(3, Types.NULL);
	        }

			int affectedRows = s.executeUpdate();
	        
	        if (affectedRows == 0) {
	        	throw new SQLException("Creating user failed, no rows affected.");
	        }
	
	        try (ResultSet generatedKeys = s.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	entity.setId(generatedKeys.getInt(1));
	            } else {
	            	throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	        
	        s.close();
	        return entity;
		} catch (SQLException ex) {
			logger.error("Error while inserting environment: "+entity.toString(), ex);
			return null;
		}
	}

	@Override
	public Environment update(Environment entity) {
		String sql = "UPDATE environments SET name = ?, location = ?, shape = ?, "
				   + "localization_type_id = ?, environment_type_id = ?, version = ?, "
				   + "operating_range = ?, parent_environment_id = ?, level = ?"
				   + " WHERE id = ?";
		
		try {
			PreparedStatement s = getConnection().prepareStatement(sql);
			s.setString(1, entity.getName());
			s.setObject(2, new PGgeometry(entity.getLocation()));
			
			if (entity.getShape() == null) {
				s.setNull(3, Types.NULL);
			} else {
				s.setObject(3, new PGgeometry(entity.getShape()));
			}

			s.setInt(4, entity.getLocalizationType().getId());
			s.setInt(5, entity.getEnvironmentType().getId());
			s.setInt(6, entity.getVersion());
			s.setDouble(7, entity.getOperatingRange());
			s.setInt(9, entity.getLevel());
			s.setInt(10, entity.getId());

			if (entity.getParent() != null) {
				s.setInt(8, entity.getParent().getId());
			} else {
				s.setNull(8, Types.NULL);
			}

			s.execute();
			s.close();

			return entity;
		} catch (SQLException ex) {
			logger.error("Error while inserting environment.", ex);
			return null;
		}
	}

	@Override
	public Environment find(Integer id) {
		String sql = "SELECT e.id, e.name, e.location, e.shape, e.operating_range, e.version, e.parent_environment_id, e.level, "
				   + "et.id as et_id, et.name as et_name, lt.id as lt_id, lt.name as lt_name, "
				   + "lt.precision as lt_precision, lt.metric as lt_metric FROM environments e "
				   + "INNER JOIN environment_types AS et ON et.id = e.environment_type_id "
				   + "INNER JOIN localization_types AS lt ON lt.id = e.localization_type_id "
				   + "WHERE e.id = ?";
		
		try {
			PreparedStatement s = getConnection().prepareStatement(sql);
			s.setInt(1, id);
	        ResultSet r = s.executeQuery();
	        
	        Environment e = null;
	        
	        while(r.next()) {
	        	e = parseResultSet(r);
	        }
	        
	        r.close();
	        s.close();
	        
	        return e;
		} catch (SQLException ex) {
			logger.error("Error while inserting environment.", ex);
			return null;
		}
	}
	
	protected Environment parseResultSet(ResultSet r) throws SQLException {
		Point location = (Point)((PGgeometry) r.getObject("location")).getGeometry();
    	
    	PGgeometry geom = (PGgeometry)r.getObject("shape");
        Polygon shape = null;
        if (geom != null) {
        	shape = (Polygon) geom.getGeometry();
        }

        EnvironmentType type = new EnvironmentType();
        type.setId(r.getInt("et_id"));
        type.setName(r.getString("et_name"));
        
        LocalizationType locType = new LocalizationType();
        locType.setId(r.getInt("lt_id"));
        locType.setName(r.getString("lt_name"));
        locType.setPrecision(r.getDouble("lt_precision"));
        locType.setMetric(r.getString("lt_metric"));

        Environment e = new Environment();
    	e.setId(r.getInt("id"));
    	e.setName(r.getString("name"));
    	e.setLocation(location);
    	e.setShape(shape);
    	e.setOperatingRange(r.getDouble("operating_range"));
    	e.setVersion(r.getInt("version"));
    	e.setEnvironmentType(type);
    	e.setLocalizationType(locType);
		e.setLevel(r.getInt("level"));

		int parentId = r.getInt("parent_environment_id");
		e.setParentId((parentId != 0) ? parentId : null);
    	
    	try {
    		e.setDistance(r.getDouble("distance_meters"));
    	} catch (SQLException ex) {}
    	
    	return e;
	}

	public Connection getConnection() {
		if (connection == null) {
			connection = DB.getConnection();
		}
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
