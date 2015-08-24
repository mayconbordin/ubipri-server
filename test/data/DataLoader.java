package data;

import dao.LogEventDAO;
import models.Device;
import models.Environment;
import models.LogEvent;
import models.User;
import org.joda.time.DateTime;

/**
 * Created by mayconbordin on 24/08/15.
 */
public class DataLoader {
    public static void populateLogs(LogEventDAO dao) {
        for (int d=1; d<=30; d++) {
            LogEvent log = createLog(new DateTime(2015, 8, d, 14, 11));
            dao.create(log);

            log = createLog(new DateTime(2015, 8, d, 14, 19));
            dao.create(log);
        }
    }

    public static LogEvent createLog(DateTime date) {
        Device device = new Device();
        device.setId(1);

        User user = new User();
        user.setId(1);

        Environment env = new Environment();
        env.setId(1);

        LogEvent log = new LogEvent();
        log.setTime(date.toDate());
        log.setDevice(device);
        log.setEnvironment(env);
        log.setUser(user);
        log.setEvent("test event");
        return log;
    }
}
