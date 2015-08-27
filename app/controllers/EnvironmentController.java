package controllers;

import java.util.List;

import javax.inject.Inject;

import org.postgis.Point;

import dao.EnvironmentDAO;
import models.Device;
import models.Environment;
import models.Functionality;
import models.User;
import models.serialization.Views;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import utils.gis.GeometryBuilder;
import utils.http.BaseController;
import utils.security.Secured;

public class EnvironmentController extends BaseController {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Inject EnvironmentDAO dao;
	
	@Security.Authenticated(Secured.class)
	public Result getAll() {
		
		Double lat = getDoubleParam("lat", null);
		Double lon = getDoubleParam("lon", null);
		Double rad = getDoubleParam("radius", 10.0);
		int limit  = getIntParam("limit", 50);
		
		List<Environment> environments;

		if (lat != null && lon != null) {
			environments = dao.findNearBy(new Point(lat, lon), rad);
		} else {
			environments = dao.findAll(limit);
		}

		return ok(environments);
	}
	
	@Security.Authenticated(Secured.class)
	public Result get(int id) {
		Environment environment = dao.find(id);
		
		if (environment == null) {
			return notFound("Environment not found");
		}

		return ok(environment);
	}
	
}
