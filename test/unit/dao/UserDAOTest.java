package unit.dao;

import static org.junit.Assert.*;
import models.User;

import org.junit.Test;

import play.Logger;
import base.ApplicationBaseTest;
import dao.UserDAO;

public class UserDAOTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	private UserDAO userDao;

	public UserDAOTest() {
		userDao = new UserDAO();
	}

	@Test
    public void testFindByWith() throws Exception {
		logger.debug("testFindByFieldCode");
		
		User user = userDao.findWith(1, "devices");
		
		assertNotNull(user);
		assertEquals("borges", user.getName());
		assertNotNull(user.getDevices());
		assertEquals(3, user.getDevices().size());
	}
}