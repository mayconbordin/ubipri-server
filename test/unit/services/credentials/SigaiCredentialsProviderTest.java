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
import play.libs.Json;

import static play.inject.Bindings.bind;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class SigaiCredentialsProviderTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private CredentialsProvider provider;
	
	@Before
	public void setUp() throws Exception {
		String content = 
				"{\"id\":51,\"matricula\":\"3456\",\"nome\":\"Dione Taschetto\",\"email\":\"dione_taschetto@gmail.com\"" +
				",\"display_name\":\"3456 | Dione Taschetto\",\"roles\":[{\"id\":2,\"name\":\"professor\"," +
				"\"display_name\":\"Professor\",\"description\":null}]}";
		
		Map<String, Object> userInfo = Json.mapper().readValue(content, HashMap.class);
		
		ISigaiService sigaiMock = mock(ISigaiService.class);
		when(sigaiMock.authenticateUser("1234", "12345")).thenReturn(true);
		when(sigaiMock.authenticateUser("3456", "12345")).thenReturn(true);
		when(sigaiMock.getUserInfo()).thenReturn(userInfo);
		
		Injector injector = new GuiceInjectorBuilder()
			    .bindings(new DAOModule())
			    .overrides(bind(ISigaiService.class).toInstance(sigaiMock))
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
		
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("dione", user.getName());
	}
}
