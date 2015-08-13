package utils.db;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Action;
import models.Environment;
import models.EnvironmentFrequencyLevel;
import models.UserEnvironment;

import org.joda.time.DateTime;
import org.postgis.Point;

import play.db.DB;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

import dao.ebean.EnvironmentEbeanDAO;

public class FixturesUtil {
	public static void load(String path) {
		load(path, DB.getConnection());
	}
	
	public static void load(String path, Connection dbConn) {
		Map<String, List<Object>> tableMap = (Map<String, List<Object>>) Yaml.load(path);
		
		for (Map.Entry<String, List<Object>> tableEntry : tableMap.entrySet()) {
			if (tableEntry.getKey().startsWith("_")) continue;
			
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
			
			if (tableEntry.getKey().equals("actions")) {
				for (Object o : tableEntry.getValue()) {
					Action a = (Action) o;
					
					DateTime dt = new DateTime(2015, 2, 1, 8, 0, 0);
					
					a.setStartDate(dt.toDate());
					a.setEndDate(dt.toDate());
					a.setDurationInterval(100);
					a.setStartDailyInterval(10);
		    	}
			}
			
		    if (tableEntry.getKey().equals("environments")) {
		    	EnvironmentEbeanDAO dao = new EnvironmentEbeanDAO();
		    	dao.setConnection(dbConn);
		    	
		    	for (Object o : tableEntry.getValue()) {
		    		Environment e = (Environment) o;
		    		e.setLocation(new Point(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ()));
		    		
		    		dao.create(e);
		    	}
		    } else {
		    	Ebean.save(tableEntry.getValue());
		    }
		}
	}
}
