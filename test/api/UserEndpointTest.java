package api;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import java.util.Map;

import base.GuiceApplicationBaseTest;
import forms.UserDeviceForm;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import base.ApplicationBaseTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import play.Logger;
import play.libs.Json;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;

public class UserEndpointTest extends GuiceApplicationBaseTest {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Test
    public void testGetUser() throws Exception {
		logger.debug("testGetUser");
    	RequestBuilder request = buildRequest().method(GET).uri("/user");

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);
    	
    	String expected = "{name:'borges', emailAddress:'guilhermeborges@gmail.com',fullName:'Guilherme A. Borges'}";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
	
	@Test
    public void testUpdateUser() throws Exception {
		logger.debug("testUpdateUser");
		
		String name = "borges";
		String email = "guilhermeborges@gmail.com";
		String fullName = "Guilherme Antônio Borges";
		
    	RequestBuilder request = buildRequest().method(PUT).uri("/user")
			.bodyForm(ImmutableMap.of("name", name, "emailAddress", email,
					"fullName", fullName));

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);
    	
    	String expected = String.format("{name:'%s', emailAddress:'%s',fullName:'%s'}",
    			name, email, fullName);
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
	
	@Test
    public void testListUserDevices() throws Exception {
		logger.debug("testListUserDevices");
    	RequestBuilder request = buildRequest().method(GET).uri("/user/devices");

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

    	String expected = "[{code:'1234554321'},{code:'1111111111'},{code:'5E-EF-8-9B-54'}]";
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }
	
	@Test
    public void testAddDevice() throws Exception {
		logger.debug("testAddDevice");

		UserDeviceForm form = new UserDeviceForm();
		form.setCode("698dc19d489c4e4db73e28a713eab07b");
		form.setName("Samsung Galaxy S5");
		form.setDeviceType("Android");
		form.addFunctionality("Wi-Fi");
		form.addFunctionality("GPS");
		
		RequestBuilder request = buildRequest().method(POST).uri("/user/devices")
				.bodyJson(Json.toJson(form));

		Result result = route(request);
    	assertEquals(Status.CREATED, result.status());
		assertIsJson(result);

		logger.info(contentAsString(result));

    	String expected = String.format("{code:'%s',name:'%s',type:{id:1,name:'%s'}}",
				form.getCode(), form.getName(), form.getDeviceType());
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    	
    	// List all devices again, expecting new device to be there too
    	request = buildRequest().method(GET).uri("/user/devices");

    	result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);

    	expected = String.format("[{code:'1234554321'},{code:'1111111111'},{code:'5E-EF-8-9B-54'},{code:'%s'}]", form.getCode());
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
	}

	@Test
	public void testAddDeviceAlreadyExists() throws Exception {
		logger.debug("testAddDeviceAlreadyExists");

		String code = "1111111111";
		String name = "Tablet Lab. 205";
		String type = "Android";

		RequestBuilder request = buildRequest().method(POST).uri("/user/devices")
				.bodyForm(ImmutableMap.of("code", code, "name", name, "deviceType", type));

		Result result = route(request);
		assertEquals(Status.CONFLICT, result.status());
		assertIsJson(result);
	}
	
	@Test
    public void testUpdateLocation() throws Exception {
		logger.debug("testUpdateLocation");
    	RequestBuilder request = buildRequest().method(PUT).uri("/user/location")
    			.bodyForm(ImmutableMap.of(
    					"deviceCode", "1234554321",
    					"environmentId", "1"
				));

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertIsJson(result);
    	
    	logger.info(contentAsString(result));

    	String expected = "[{'accessLevel':{'accessType':{'name':'Administrative'},'environmentType':{'name':'Public'}},'functionality':{'name':'Wi-Fi'},'action':'on'}]";
    	
    	JSONAssert.assertEquals(expected, contentAsString(result), false);
    }

	@Test
	public void testUpdateLocationNewOnEnvironment() throws Exception {
		logger.debug("testUpdateLocationNewOnEnvironment");
		RequestBuilder request = buildRequest().method(PUT).uri("/user/location")
				.bodyForm(ImmutableMap.of(
						"deviceCode", "1234554321",
						"environmentId", "6"
				));

		Result result = route(request);
		assertEquals(Status.OK, result.status());
		assertIsJson(result);

		logger.info(contentAsString(result));

		//String expected = "[{'accessLevel':{'accessType':{'name':'Administrative'},'environmentType':{'name':'Public'}},'functionality':{'name':'Wi-Fi'},'action':'on'}]";

		//JSONAssert.assertEquals(expected, contentAsString(result), false);
	}
}
