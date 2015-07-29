package models;

import utils.db.PostGisPlatform;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.ServerConfigStartup;

public class EbeanServerConfigStartup implements ServerConfigStartup 
{
	@Override
	public void onStart(ServerConfig c) {
		c.setDatabasePlatform(new PostGisPlatform());
		
		
		c.addPackage("utils.db.types");
	}
}