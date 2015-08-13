package dao;

import org.joda.time.Period;

import models.Environment;
import models.LogEvent;
import models.User;

public interface LogEventDAO extends BaseDAO<LogEvent, Integer> {
	public int count(User user, Environment environment, Period period);
}
