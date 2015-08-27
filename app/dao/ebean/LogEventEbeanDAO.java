package dao.ebean;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;

import dao.LogEventDAO;
import models.Environment;
import models.LogEvent;
import models.User;

public class LogEventEbeanDAO extends BaseEbeanDAO<LogEvent, Integer> implements LogEventDAO {
	protected static final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	@Override
	public int count(User user, Environment environment, Period period) {
		return count(user, environment, period, DateTime.now());
	}

	public int count(User user, Environment environment, Period period, DateTime date) {
		DateTime start = date.minusDays(period.getDays());

		String sql = "SELECT COUNT(*) FROM (SELECT DISTINCT log_time::timestamp::date FROM log_events "
				   + "WHERE log_time::timestamp::date > ?::date AND log_time::timestamp::date <= ?::date "
				   + "AND user_id = ? AND environment_id = ? AND exiting = FALSE) AS count;";

		SqlRow sqlRow = Ebean.createSqlQuery(sql)
				.setParameter(1, dtFormatter.print(start))
				.setParameter(2, dtFormatter.print(date))
				.setParameter(3, user.getId())
				.setParameter(4, environment.getId())
				.findUnique();
				
		return (sqlRow != null) ? sqlRow.getInteger("count") : 0;
	}
}
