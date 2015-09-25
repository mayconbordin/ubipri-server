package unit.services.auth;

import base.GuiceApplicationBaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import models.OAuthGrantType;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.mayconbordin.oauth2.client.AccessToken;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.auth.SigaiAuthenticator;
import services.auth.UbiPriAuthenticator;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

public class UbiPriAuthenticatorTest extends GuiceApplicationBaseTest {
    private final Logger.ALogger logger = Logger.of(this.getClass().getSimpleName());

    private UbiPriAuthenticator auth;

    @Before
    public void setUp() throws Exception {
        auth = application.injector().instanceOf(UbiPriAuthenticator.class);
    }

    @Test
    public void testGetUser() throws Exception {
        logger.debug("testGetUser");

        Http.Request request = mock(Http.Request.class);
        when(request.headers()).thenReturn(ImmutableMap.of(
                "Authorization", new String[]{"Bearer " + getAccessToken()}
        ));

        User user = auth.getUser(request);
        assertEquals("Valderi R. Q. Leithardt", user.getFullName());
        assertEquals("valderi", user.getName());
        assertEquals("valderi@gmail.com", user.getEmailAddress());
    }

    @Test
    public void testGetUserWithoutToken() throws Exception {
        logger.debug("testGetUserWithoutToken");

        Http.Request request = mock(Http.Request.class);
        when(request.headers()).thenReturn(ImmutableMap.of());

        User user = auth.getUser(request);
        assertNull(user);
    }

    protected String getAccessToken() {
        Http.RequestBuilder request = buildRequest().method(POST).uri("/api/oauth/access_token")
                .bodyForm(ImmutableMap.of(
                        "grant_type", OAuthGrantType.PASSWORD,
                        "username", "valderi",
                        "password", "12345",
                        "client_id", "ubipri_client_id",
                        "client_secret", "secret"
                ));

        Result result = route(request);
        assertEquals(Http.Status.OK, result.status());
        assertIsJson(result);

        logger.info(contentAsString(result));

        JsonNode json = Json.parse(contentAsString(result));
        return json.get("access_token").asText();
    }
}
