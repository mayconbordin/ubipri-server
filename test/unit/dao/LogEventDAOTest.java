package unit.dao;

import base.ApplicationBaseTest;
import dao.LogEventDAO;
import dao.ebean.LogEventEbeanDAO;
import data.DataLoader;
import models.Device;
import models.Environment;
import models.LogEvent;
import models.User;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LogEventDAOTest extends ApplicationBaseTest {
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private LogEventEbeanDAO logDao;

    public LogEventDAOTest() {
        logDao = new LogEventEbeanDAO();
    }

    @Before
    public void setUp() {
        DataLoader.populateLogs(logDao);
    }

    @After
    public void tearDown() {
        logDao.deleteAll();
    }

    @Test
    public void testCountWithin30Days() throws Exception {
        logger.debug("testCountWithin30Days");

        User user = new User();
        user.setId(1);

        Environment env = new Environment();
        env.setId(1);

        int count = logDao.count(user, env, Period.days(30), new DateTime(2015, 8, 30, 14, 11));

        assertEquals(30, count);
    }

    @Test
    public void testCountWithin10Days() throws Exception {
        logger.debug("testCountWithin30Days");

        User user = new User();
        user.setId(1);

        Environment env = new Environment();
        env.setId(1);

        int count = logDao.count(user, env, Period.days(10), new DateTime(2015, 8, 24, 14, 11));

        assertEquals(10, count);
    }
}
