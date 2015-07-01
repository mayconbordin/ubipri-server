package controllers;

import javax.inject.Inject;

import models.Device;
import models.serialization.Views;
import play.Logger;
import play.mvc.Result;
import play.mvc.Security;
import dao.DeviceDAO;
import utils.http.BaseController;
import utils.security.Secured;

public class DeviceController extends BaseController {
	private final Logger.ALogger logger = Logger.of("application");
	
	@Inject DeviceDAO deviceDao;
	
	@Security.Authenticated(Secured.class)
	public Result getDevice(String code) {
		Device device = deviceDao.findByFieldWith("code", code, "functionalities");
		
		if (device == null) {
			notFound("Device not found", 404);
		}

		return ok(device, Views.Full.class);
	}
}
