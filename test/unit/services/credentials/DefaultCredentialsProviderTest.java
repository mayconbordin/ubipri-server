package unit.services.credentials;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import base.ApplicationBaseTest;
import dao.ebean.UserEbeanDAO;
import models.User;
import modules.DAOModule;
import play.Logger;
import services.SigaiService;
import services.credentials.CredentialsProvider;
import services.credentials.DefaultCredentialsProvider;

import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import static play.inject.Bindings.bind;

public class DefaultCredentialsProviderTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private CredentialsProvider provider;
	
	@Before
	public void setUp() {
		Injector injector = new GuiceInjectorBuilder()
			    .bindings(new DAOModule())
			    .overrides(bind(CredentialsProvider.class).to(DefaultCredentialsProvider.class))
			    .injector();
		
		provider = injector.instanceOf(CredentialsProvider.class);
	}
	
	@Test
	public void testAuthenticate() {
		logger.debug("testAuthenticate");
		
		User user = provider.authenticate("guilhermeborges@gmail.com", "12345");
		
		assertNotNull(user);
		assertEquals("borges", user.getName());
	}
	
	@Test
	public void testRegister() {
		logger.debug("testRegister");
		
		User user = provider.register(null, null, ImmutableMap.of(
			"name", "teste",
			"email", "teste@teste.com.br",
			"fullName", "Teste",
			"password", "12345"
		));
		
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("teste", user.getName());
	}
}
