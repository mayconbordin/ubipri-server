package dao;

import java.util.List;
import org.postgis.Point;
import models.Environment;

public interface EnvironmentDAO extends BaseDAO<Environment, Integer> {
	public List<Environment> findAll();
	public List<Environment> findAll(Integer limit) ;
	public List<Environment> findAll(Integer limit, Integer offset);
	public List<Environment> findNearBy(Point location, double radius);
}
