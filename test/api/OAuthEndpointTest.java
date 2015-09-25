package api;

import base.GuiceApplicationBaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import forms.UserDeviceForm;
import models.OAuthGrantType;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import play.Logger;
import play.libs.Json;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class OAuthEndpointTest extends GuiceApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Test
    public void testClientCredentials() throws Exception {
		logger.debug("testClientCredentials");
    	RequestBuilder request = buildRequest().method(POST).uri("/api/oauth/access_token")
				.bodyForm(ImmutableMap.of(
					"grant_type", OAuthGrantType.CLIENT_CREDENTIALS,
					"username", "valderi",
					"password", "12345",
					"client_id", "ubipri_client_id_2",
					"client_secret", "secret"
				));

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

		logger.info(contentAsString(result));
    }

	@Test
	public void testPassword() throws Exception {
		logger.debug("testPassword");
		RequestBuilder request = buildRequest().method(POST).uri("/api/oauth/access_token")
				.bodyForm(ImmutableMap.of(
						"grant_type", OAuthGrantType.PASSWORD,
						"username", "valderi",
						"password", "12345",
						"client_id", "ubipri_client_id",
						"client_secret", "secret"
				));

		Result result = route(request);
		assertEquals(Status.OK, result.status());
		assertIsJson(result);

		logger.info(contentAsString(result));
	}

	@Test
	public void testPasswordAndRefresh() throws Exception {
		logger.debug("testPasswordAndRefresh");
		RequestBuilder request = buildRequest().method(POST).uri("/api/oauth/access_token")
				.bodyForm(ImmutableMap.of(
						"grant_type", OAuthGrantType.PASSWORD,
						"username", "valderi",
						"password", "12345",
						"client_id", "ubipri_client_id",
						"client_secret", "secret"
				));

		Result result = route(request);
		assertEquals(Status.OK, result.status());
		assertIsJson(result);

		logger.info(contentAsString(result));

		JsonNode json = Json.parse(contentAsString(result));
		String refreshToken = json.get("refresh_token").asText();

		logger.info("Refresh token: " + refreshToken);

		request = buildRequest().method(POST).uri("/api/oauth/access_token")
				.bodyForm(ImmutableMap.of(
						"grant_type", OAuthGrantType.REFRESH_TOKEN,
						"client_id", "ubipri_client_id",
						"client_secret", "secret",
						"refresh_token", refreshToken
				));

		result = route(request);
		assertEquals(Status.OK, result.status());
		assertIsJson(result);
	}
}
