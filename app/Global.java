import org.postgis.geojson.PostGISModule;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.*;
import play.libs.Json;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        Logger.info("Application has started");
        
        ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new PostGISModule());
	    
	    Json.setObjectMapper(mapper);
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}