package unit.services;

import org.junit.Before;
import org.junit.Test;

import base.ApplicationBaseTest;
import dao.ebean.EnvironmentEbeanDAO;
import models.Environment;
import modules.DAOModule;
import play.Logger;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import services.IPrivacyService;
import services.ISigaiService;
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
			    .overrides(bind(IPrivacyService.class).to(PrivacyService.class))
			    .injector();
		
		service = injector.instanceOf(IPrivacyService.class);
	}
	
	@Test
	public void testUpdateUserFrequency() {
		logger.debug("testUpdateUserFrequency");
		
		Environment e = new EnvironmentEbeanDAO().find(1);
		
		service.updateUserFrequency(null, e);
	}
}
