package api;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import play.Logger;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import base.ApplicationBaseTest;

public class DeviceEndpointTest extends ApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Test
    public void testGetDevice() throws Exception {
		logger.debug("testGetDevice");
		
		String code = "1234554321";
		
		RequestBuilder request = buildRequest().method(GET).uri("/user/devices/"+code);

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);
    	
    	logger.info(contentAsString(result));
    	
    	String expected = String.format("{name:'VitualMachineAndroid_2.3',code:'%s'}", code);
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    	
    	expected = "{type:{name:'VMAndroid'}}";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    	
    	expected = "{functionalities:[{id:1},{id:2},{id:3},{id:5},{id:9},{id:15}]}";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }

}
