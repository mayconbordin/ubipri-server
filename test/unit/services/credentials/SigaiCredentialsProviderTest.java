package unit.services.credentials;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import base.ApplicationBaseTest;
import dao.ebean.UserEbeanDAO;
import models.User;
import modules.DAOModule;
import modules.ServiceModule;
import play.Logger;
import services.ISigaiService;
import services.SigaiService;
import services.credentials.CredentialsProvider;
import services.credentials.DefaultCredentialsProvider;
import services.credentials.SigaiCredentialsProvider;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import static play.inject.Bindings.bind;

public class SigaiCredentialsProviderTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private CredentialsProvider provider;
	
	@Before
	public void setUp() {
		Injector injector = new GuiceInjectorBuilder()
			    .bindings(new DAOModule())
			    .overrides(bind(ISigaiService.class).to(SigaiService.class))
			    .overrides(bind(CredentialsProvider.class).to(SigaiCredentialsProvider.class))
			    .injector();
		
		provider = injector.instanceOf(CredentialsProvider.class);
	}
	
	@Test
	public void testAuthenticate() {
		logger.debug("testAuthenticate");
		
		User user = provider.authenticate("1234", "12345");
		
		assertNotNull(user);
		assertEquals("valderi", user.getName());
	}
	
	@Test
	public void testRegister() {
		logger.debug("testRegister");
		
		User user = provider.register("3456", "12345", null);
		
		System.out.println(user.toString());
		
		/*assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("teste", user.getName());*/
	}
}
