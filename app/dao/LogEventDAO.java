package dao;

import org.joda.time.DateTime;
import org.joda.time.Period;

import models.Environment;
import models.LogEvent;
import models.User;

public interface LogEventDAO extends BaseDAO<LogEvent, Integer> {
	/**
	 * Counts the number of logs in a given period between (date - period) and (date). Multiple occurrences in a single day
	 * are ignored.
	 *
	 * @param user
	 * @param environment
	 * @param period
	 * @param date
	 * @return
	 */
	public int count(User user, Environment environment, Period period, DateTime date);

	/**
	 * Counts the number of logs in a given period between (current date - period) and (current date). Multiple occurrences
	 * in a single day are ignored.
	 *
	 * @param user
	 * @param environment
	 * @param period
	 * @return
	 */
	public int count(User user, Environment environment, Period period);
}
