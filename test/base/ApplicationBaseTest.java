package base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgis.Point;
import org.postgis.Polygon;

import models.Device;
import models.Environment;
import models.UserEnvironment;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import dao.EnvironmentDAO;
import play.Logger;
import play.test.*;
import play.libs.Json;
import play.libs.Yaml;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.*;
import utils.db.FixturesUtil;


public class ApplicationBaseTest extends WithApplication {
	private final Logger.ALogger logger = Logger.of("TEST");
	
	protected FakeApplication application;
	protected Database database;
	
	protected Map<String,String> setupDatabase() {
		String driver = "org.postgresql.Driver";
		String url    = "jdbc:postgresql://localhost/ubipri_test";
		
		database = Databases.createFrom(driver, url, ImmutableMap.of(
            "username", "postgres",
            "password", "postgres"
        ));
        
		Evolutions.applyEvolutions(database);

        return ImmutableMap.of(
			"db.default.driver", driver,
			"db.default.url", url
		);
    }

	@Override
    protected FakeApplication provideFakeApplication() {
		File path 				   = new File(".");
		ClassLoader classLoader    = Helpers.class.getClassLoader();
		Map<String, Object> config = new HashMap<String, Object>();
		List<String> plugins 	   = new ArrayList<String>();
		
		config.putAll(setupDatabase());
		config.put("logger.application", "DEBUG");
		
		application = new FakeApplication(path, classLoader, config, plugins, null);

		return application;
	}

	@Override
	public void startPlay() {
		super.startPlay();
		
		FixturesUtil.load("fixtures.yaml", database.getConnection());
	}

	@Override
	public void stopPlay() {
		Evolutions.cleanupEvolutions(database);
		database.shutdown();
		super.stopPlay();
	}
	
	protected String getAuthToken() {
		RequestBuilder request = new RequestBuilder().method(POST).uri("/login")
    			.bodyForm(ImmutableMap.of(
    					"emailAddress", "guilhermeborges@gmail.com",
    					"password", "12345"));

    	Result result = route(request);
    	assertEquals(Status.OK, result.status());
    	assertEquals("application/json", result.contentType());
        assertEquals("utf-8", result.charset());
        
        JsonNode json = Json.parse(contentAsString(result));
        return json.get("authToken").asText();
	}
	
	protected RequestBuilder buildRequest() {
		return new RequestBuilder().header("X-AUTH-TOKEN", getAuthToken());
	}
	
	protected void assertIsJson(Result result) {
		assertEquals("application/json", result.contentType());
	}
	
	protected void assertJsonContent(Result result, Map<String, String> values) {
		assertEquals("application/json", result.contentType());
        assertEquals("utf-8", result.charset());
    	
        JsonNode json = Json.parse(contentAsString(result));

        for (Map.Entry<String, String> e : values.entrySet()) {
        	assertTrue(json.has(e.getKey()));
        	assertEquals(e.getValue(), json.get(e.getKey()).asText());
        }
	}
}
