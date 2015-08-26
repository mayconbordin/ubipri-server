package base;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import play.Application;
import play.Logger;
import play.Mode;
import play.api.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.libs.Json;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import services.auth.Authenticator;
import utils.db.FixturesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.inject.Bindings.bind;


public class GuiceApplicationBaseTest extends WithApplication {
	private final Logger.ALogger logger = Logger.of("GuiceApplicationBaseTest");
	
	protected Application application;
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
	protected Application provideApplication() {
		File path 				   = new File(".");
		ClassLoader classLoader    = Helpers.class.getClassLoader();
		Map<String, Object> config = new HashMap<String, Object>();
		List<String> plugins 	   = new ArrayList<String>();
		
		config.putAll(setupDatabase());
		config.put("logger.application", "DEBUG");

		application = new GuiceApplicationBuilder()
				.configure(config)
				.in(path)
				.in(classLoader)
				.in(Mode.TEST)
				.overrides(bind(Authenticator.class).to(MockAuthenticator.class))
				.build();

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
	
	protected RequestBuilder buildRequest() {
		return new RequestBuilder().header("Authorization", "Bearer #test-token#");
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
