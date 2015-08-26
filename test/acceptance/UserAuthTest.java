package acceptance;

import base.GuiceApplicationBaseTest;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

/**
 * Verifies if the authentication using SIGA-i is working. Requires the SIGA-i system to be working properly and its
 * configurations have to be set in {@link OAuthConstants}.
 */
public class UserAuthTest extends GuiceApplicationBaseTest {
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private String accessToken;

    public UserAuthTest() {
        accessToken = OAuthClient.getAccessToken();
    }

    @Override
    protected Application buildApplication(GuiceApplicationBuilder builder) {
        return builder.build();
    }

    @Override
    protected Http.RequestBuilder buildRequest() {
        return new Http.RequestBuilder().header("Authorization", "Bearer "+accessToken);
    }

    @Test
    public void testGetUser() throws Exception {
        logger.debug("testGetUser");
        Http.RequestBuilder request = buildRequest().method(GET).uri("/user");

        Result result = route(request);
        assertEquals(Http.Status.OK, result.status());
        assertIsJson(result);

        String expected = "{name:'dione', emailAddress:'dione_taschetto@gmail.com',fullName:'Dione Taschetto'}";
        JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
}
