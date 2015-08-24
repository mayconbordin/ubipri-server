package unit.services;

import dao.LogEventDAO;
import data.DataLoader;
import forms.UserLocationForm;
import models.EnvironmentFrequencyLevel;
import models.FrequencyLevel;
import models.User;
import modules.ServiceModule;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import base.ApplicationBaseTest;
import models.Environment;
import modules.DAOModule;
import play.Logger;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import services.IPrivacyService;
import services.PrivacyService;

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
				.build();

		service = injector.instanceOf(IPrivacyService.class);

		LogEventDAO logDao = injector.instanceOf(LogEventDAO.class);
		DataLoader.populateLogs(logDao);
	}

	@Test
	public void testChangeUserLocation() {
		logger.debug("testChangeUserLocation");

		User u = new User();
		u.setId(1);

		UserLocationForm form = new UserLocationForm();
		form.setDeviceCode("1234554321");
		form.setEnvironmentId(1);
		form.setExiting(false);

		service.changeUserLocation(u, form);
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
