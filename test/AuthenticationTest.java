import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import org.junit.Test;

import play.libs.Json;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import base.ApplicationBaseTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

public class AuthenticationTest extends ApplicationBaseTest {
	@Test
    public void testLogin() {
    	RequestBuilder request = new RequestBuilder().method(POST).uri("/login")
    			.bodyForm(ImmutableMap.of(
    					"emailAddress", "guilhermeborges@gmail.com",
    					"password", "12345"));

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertEquals("application/json", result.contentType());
        assertEquals("utf-8", result.charset());
        
        JsonNode json = Json.parse(contentAsString(result));
        assertTrue(json.has("authToken"));
    }
	
	@Test
    public void testMissingEmail() {
    	RequestBuilder request = new RequestBuilder().method(POST).uri("/login")
    			.bodyForm(ImmutableMap.of("password", "12345"));

    	Result result = route(request);
    	assertEquals(Status.BAD_REQUEST, result.status());
    	assertEquals("application/json", result.contentType());
        assertEquals("utf-8", result.charset());
        
        JsonNode json = Json.parse(contentAsString(result));
        assertTrue(json.has("emailAddress"));
    }
	
	@Test
    public void testLogoutNoToken() {
    	RequestBuilder request = new RequestBuilder().method(POST).uri("/logout");

    	Result result = route(request);
    	assertEquals(Status.UNAUTHORIZED, result.status());
    }
	
	@Test
    public void testLogout() {
		String authToken = getAuthToken();
    	RequestBuilder request = new RequestBuilder().method(POST).uri("/logout")
    			.header("X-AUTH-TOKEN", authToken);

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	
    	/*request = new RequestBuilder().method(POST).uri("/logout")
    			.header("X-AUTH-TOKEN", authToken);*/

    	result = route(request);
    	assertEquals(Status.UNAUTHORIZED, result.status());
    }
}
