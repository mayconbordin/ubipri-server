package unit.services.auth;

import base.GuiceApplicationBaseTest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import junit.framework.Assert;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.mayconbordin.oauth2.client.AccessToken;
import play.Logger;
import play.mvc.Http;
import services.auth.SigaiAuthenticator;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SigaiAuthenticatorTest extends GuiceApplicationBaseTest {
    private static final String verifyResponse = "{\"access_token\":\"xkyqY9GpxGH2ef0dqNWO1yRB50yRv3ylIyezGQxO\""
            + ",\"valid\":true,\"user\":{\"id\":51,\"matricula\":\"3456\",\"nome\":\"Dione Taschetto\",\"email\":\"dione_taschetto@gmail.com\","
            + "\"display_name\":\"3456 | Dione Taschetto\",\"roles\":[{\"id\":2,\"name\":\"professor\",\"display_name\":\"Professor\","
            + "\"description\":null}]}}";

    private final Logger.ALogger logger = Logger.of(this.getClass().getSimpleName());

    private SigaiAuthenticator auth;

    @Before
    public void setUp() throws Exception {
        auth = application.injector().instanceOf(SigaiAuthenticator.class);


    }

    @Test
    public void testGetUser() throws Exception {
        logger.debug("testGetUser");

        String tokenStr = "testToken123";

        Http.Request request = mock(Http.Request.class);
        when(request.headers()).thenReturn(ImmutableMap.of(
            "Authorization", new String[] {"Bearer "+tokenStr}
        ));

        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.getResource(anyString())).thenReturn(verifyResponse);

        auth.setAccessToken(accessToken);

        User user = auth.getUser(request);
        assertEquals("Dione Taschetto", user.getFullName());
        assertEquals("dione", user.getName());
        assertEquals("dione_taschetto@gmail.com", user.getEmailAddress());
        assertEquals(1, user.getCredentials().size());
    }

    @Test
    public void testRegister() {
        logger.debug("testRegister");

        Map<String, Object> data = ImmutableMap.of(
            "matricula", "1234",
            "nome", "Test test",
            "email", "test@test.com",
            "roles", ImmutableList.of(ImmutableMap.of("display_name", "Professor"))
        );

        User user = auth.register(data);

        assertEquals(data.get("nome"), user.getFullName());
        assertEquals("test", user.getName());
        assertEquals(data.get("email"), user.getEmailAddress());
        assertEquals(1, user.getCredentials().size());

        assertEquals(SigaiAuthenticator.NAME, user.getCredentials().get(0).getSystem());
        assertEquals(data.get("matricula"), user.getCredentials().get(0).getExternalId());
    }

    @Test
    public void testVerifyToken() throws Exception {
        logger.debug("testVerifyToken");

        AccessToken accessToken = mock(AccessToken.class);
        when(accessToken.getResource(anyString())).thenReturn("{}");

        auth.setAccessToken(accessToken);

        Map<String, Object> data = auth.verifyToken("test");
        assertEquals(0, data.size());

        when(accessToken.getResource(anyString())).thenReturn("{\"name\":\"test\"}");
        data = auth.verifyToken("test");
        assertEquals(1, data.size());
    }
}
