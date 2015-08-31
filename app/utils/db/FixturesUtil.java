package utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Action;
import models.Environment;
import models.EnvironmentFrequencyLevel;
import models.UserEnvironment;

import org.joda.time.DateTime;
import org.postgis.Point;

import org.postgis.Polygon;
import play.Logger;
import play.db.DB;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

import dao.ebean.EnvironmentEbeanDAO;

public class FixturesUtil {
	private static final Logger.ALogger LOGGER = Logger.of("FixturesUtil");
	public static void load(String path) {
		load(path, DB.getConnection());
	}
	
	public static void load(String path, Connection dbConn) {
		Map<String, List<Object>> tableMap = (Map<String, List<Object>>) Yaml.load(path);

		List<Object> shapes = tableMap.get("_shapes");
		
		for (Map.Entry<String, List<Object>> tableEntry : tableMap.entrySet()) {
			if (tableEntry.getKey().startsWith("_")) continue;

			LOGGER.debug("Loading table "+tableEntry.getKey());

			if (tableEntry.getKey().equals("user_in_environment")) {
				for (Object o : tableEntry.getValue()) {
					UserEnvironment ue = (UserEnvironment) o;
					
					ue.getPk().setUserId(ue.getUser().getId());
					ue.getPk().setEnvironmentId(ue.getEnvironment().getId());
		    	}
			}
			
			if (tableEntry.getKey().equals("environment_frequency_levels")) {
				for (Object o : tableEntry.getValue()) {
					EnvironmentFrequencyLevel efl = (EnvironmentFrequencyLevel) o;
					
					efl.getPk().setEnvironmentId(efl.getEnvironment().getId());
					efl.getPk().setFrequencyLevelId(efl.getFrequencyLevel().getId());
		    	}
			}
			
		    if (tableEntry.getKey().equals("environments")) {
		    	EnvironmentEbeanDAO dao = new EnvironmentEbeanDAO();
		    	dao.setConnection(dbConn);

				for (int i=0; i<tableEntry.getValue().size(); i++) {
					Environment e = (Environment) tableEntry.getValue().get(i);
					e.setLocation(new Point(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ()));

					if (i < shapes.size()) {
						try {
							Polygon shape = new Polygon((String) shapes.get(i));
							shape.setSrid(4326);
							e.setShape(shape);
						} catch (SQLException ex) {
							LOGGER.error("Unable to parse polygon shape.", ex);
						}
					}

					dao.create(e);
				}
		    } else {
		    	Ebean.save(tableEntry.getValue());
		    }
		}
	}
}
