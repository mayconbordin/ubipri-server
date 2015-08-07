package unit.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import base.ApplicationBaseTest;
import play.Logger;
import services.SigaiService;

public class SigaiServiceTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private SigaiService service;
	
	@Before
	public void setUp() {
		service = new SigaiService();
	}
	
	@Test
	public void testAuthenticate() {
		logger.debug("testAuthenticate");
		boolean ok = service.authenticateUser("1234", "12345");
		assertTrue(ok);
	}
	
	@Test
	public void testAuthenticateWrongCredentials() {
		logger.debug("testAuthenticateWrongCredentials");
		boolean ok = service.authenticateUser("1234", "123456");
		assertFalse(ok);
	}
	
	@Test
	public void testGetUserInfo() {
		logger.debug("testGetUserInfo");
		service.authenticateUser("1234", "12345");
		service.getUserInfo();
		
		//System.out.println(userInfo);
	}
}
