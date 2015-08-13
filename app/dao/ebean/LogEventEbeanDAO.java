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
	
	public int count(User user, Environment environment, Period period) {
		DateTime now = DateTime.now().minusDays(period.getDays());

		String sql = "select count(*) as count from log_events where log_time::timestamp::date > ?::date "
				+ "group by log_time::timestamp::date";
		
		SqlRow sqlRow = Ebean.createSqlQuery(sql)
				.setParameter(1, dtFormatter.print(now))
				.findUnique();
				
		return (sqlRow != null) ? sqlRow.getInteger("count") : 0;
	}
}
