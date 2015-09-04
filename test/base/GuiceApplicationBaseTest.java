package base;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import play.Application;
import play.Logger;
import play.Mode;
import play.api.inject.Binding;
import play.inject.guice.GuiceApplicationBuilder;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.libs.Json;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
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

	protected Map<String,String> getDatabaseConfig() {
		return ImmutableMap.of(
			"driver", "org.postgresql.Driver",
			"url", "jdbc:postgresql://localhost/ubipri_test",
			"username", "postgres",
			"password", "postgres"
		);
	}

	protected Map<String,String> setupDatabase(Map<String,String> config) {
		database = Databases.createFrom(config.get("driver"), config.get("url"), ImmutableMap.of(
			"username", config.get("username"),
			"password", config.get("password")
		));

		Evolutions.applyEvolutions(database);

		return ImmutableMap.of(
			"db.default.driver", config.get("driver"),
			"db.default.url"   , config.get("url")
		);
	}

	protected Map<String,String> setupDatabase() {
		return setupDatabase(getDatabaseConfig());
    }

	@Override
	protected Application provideApplication() {
		File path 				   = new File(".");
		ClassLoader classLoader    = Helpers.class.getClassLoader();
		Map<String, Object> config = new HashMap<String, Object>();

		config.putAll(setupDatabase());
		config.put("logger.application", "DEBUG");

		application = buildApplication(new GuiceApplicationBuilder()
				.configure(config).in(path).in(classLoader).in(Mode.TEST));

		return application;
	}

	protected Application buildApplication(GuiceApplicationBuilder builder) {
		return builder.overrides(bind(Authenticator.class).to(MockAuthenticator.class))
				.build();
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

	protected List<Binding<?>> getOverrides() {
		List<Binding<?>> overrides = new ArrayList<>();
		overrides.add(bind(Authenticator.class).to(MockAuthenticator.class));
		return overrides;
	}
	
	protected RequestBuilder buildRequest() {
		return new RequestBuilder().header("Authorization", "Bearer access_token");
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
