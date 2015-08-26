package unit.services;

import dao.LogEventDAO;
import data.DataLoader;
import forms.UserLocationForm;
import models.*;
import modules.ServiceModule;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import base.ApplicationBaseTest;
import modules.DAOModule;
import play.Logger;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import services.IClock;
import services.IPrivacyService;
import services.PrivacyService;

import java.util.List;

import static org.junit.Assert.*;
import static play.inject.Bindings.bind;

public class PrivacyServiceTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private IPrivacyService service;
	
	@Before
	public void setUp() {
		Injector injector = new GuiceInjectorBuilder()
				.bindings(new DAOModule())
				.bindings(new ServiceModule(play.Environment.simple(), application.configuration()))
				.overrides(bind(IPrivacyService.class).to(PrivacyService.class))
				.overrides(bind(IClock.class).to(MockClock.class))
				.build();

		service = injector.instanceOf(IPrivacyService.class);

		LogEventDAO logDao = injector.instanceOf(LogEventDAO.class);
		DataLoader.populateLogs(logDao);

		MockClock.setDateTime(new DateTime(2015, 8, 26, 8, 50));
	}

	@Test
	public void testChangeUserLocation() {
		logger.debug("testChangeUserLocation");
		logger.debug("User entering a public location");

		User user = new User();
		user.setId(1);

		UserLocationForm form = new UserLocationForm("1234554321", 1, false);

		List<Action> actions = service.changeUserLocation(user, form);

		logger.info("Size of actions: "+actions.size());

		assertEquals(1, actions.size());
		assertEquals("Wi-Fi", actions.get(0).getFunctionality().getName());
		assertEquals("on", actions.get(0).getAction());
	}

	@Test
	public void testChangeUserLocationPrivateEnv() {
		logger.debug("testChangeUserLocation");
		logger.debug("User entering a private location");

		User user = new User();
		user.setId(1);

		UserLocationForm form = new UserLocationForm("1234554321", 2, false);

		List<Action> actions = service.changeUserLocation(user, form);

		logger.info("Size of actions: "+actions.size());

		assertEquals(1, actions.size());
		assertEquals("Wi-Fi", actions.get(0).getFunctionality().getName());
		assertEquals("on", actions.get(0).getAction());
	}

	@Test
	public void testChangeUserLocationPrivateEnv2() {
		logger.debug("testChangeUserLocation2");
		logger.debug("User entering a private location");

		User user = new User();
		user.setId(1);

		UserLocationForm form = new UserLocationForm("1234554321", 5, false);

		List<Action> actions = service.changeUserLocation(user, form);

		logger.info("Size of actions: " + actions.size());

		assertEquals(2, actions.size());
		assertEquals("Wi-Fi", actions.get(0).getFunctionality().getName());
		assertEquals("on", actions.get(0).getAction());
		assertEquals("GPS", actions.get(1).getFunctionality().getName());
		assertEquals("on", actions.get(1).getAction());
	}

	@Test
	public void testGetUserFrequency() {
		logger.debug("testGetUserFrequency");
		
		Environment e = new Environment();
		e.setId(1);

		User u = new User();
		u.setId(1);

		EnvironmentFrequencyLevel level = service.getUserFrequencyLevel(u, e, new DateTime(2015, 8, 30, 14, 11));
		assertEquals(e.getId(), level.getEnvironment().getId());
		assertEquals(FrequencyLevel.FREQUENT, level.getFrequencyLevel().getName());
	}

	@Test
	public void testGetUserFrequencyNormal() {
		logger.debug("testGetUserFrequencyNormal");

		Environment e = new Environment();
		e.setId(1);

		User u = new User();
		u.setId(1);

		EnvironmentFrequencyLevel level = service.getUserFrequencyLevel(u, e, new DateTime(2015, 8, 9, 14, 11));
		assertEquals(e.getId(), level.getEnvironment().getId());
		assertEquals(FrequencyLevel.NORMAL, level.getFrequencyLevel().getName());
	}

	@Test
	public void testGetUserFrequencyLessFrequent() {
		logger.debug("testGetUserFrequencyLessFrequent");

		Environment e = new Environment();
		e.setId(1);

		User u = new User();
		u.setId(1);

		EnvironmentFrequencyLevel level = service.getUserFrequencyLevel(u, e, new DateTime(2015, 8, 1, 14, 11));
		assertEquals(e.getId(), level.getEnvironment().getId());
		assertEquals(FrequencyLevel.LESS_FREQUENT, level.getFrequencyLevel().getName());
	}
}
