package api;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

import base.GuiceApplicationBaseTest;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.google.common.collect.ImmutableMap;

import play.Logger;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import base.ApplicationBaseTest;

public class EnvironmentEndpointTest extends GuiceApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Test
    public void testListEnvironments() throws Exception {
		logger.debug("testListEnvironments");
		
    	RequestBuilder request = buildRequest().method(GET)
    			.uri("/environments");

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

		logger.info(contentAsString(result));

    	String expected = "[{id:1},{id:2},{id:3},{id:4},{id:5}]";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
	
	@Test
    public void testListEnvironmentsWithRadius() throws Exception {
		logger.debug("testListEnvironmentsWithRadius");
		
		double lat = -30.072296142578118;
		double lon = -51.17763595581054;
		double radius = 2000.0; // meters
		
    	RequestBuilder request = buildRequest().method(GET)
    			.uri("/environments?lat="+lat+"&lon="+lon+"&radius="+radius);

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

    	String expected = "[{id:1}]";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
	
	@Test
    public void testGetEnvironment() throws Exception {
		logger.debug("testGetEnvironment");
				
    	RequestBuilder request = buildRequest().method(GET)
    			.uri("/environments/1");

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

    	String expected = "{id:1,name:'Porto Alegre'}";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }

}
