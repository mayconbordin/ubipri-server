package controllers;

import java.util.List;

import javax.inject.Inject;

import dao.DeviceDAO;
import models.Device;
import models.Functionality;
import models.User;
import models.serialization.Views;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
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
	
	@Security.Authenticated(Secured.class)
	public Result updateDevice(String code) {
		Device device = deviceDao.findByField("code", code);
		
		if (device == null) {
			notFound("Device not found", 404);
		}
		
		Form<Device> form = Form.form(Device.class).bindFromRequest();

		if (form.hasErrors()) {
			return invalidForm(form);
		}
		
		device.setName(form.get().getName());
		device.setCode(form.get().getCode());
		deviceDao.update(device);
		
		return ok(device, Views.Full.class);
	}
	
	@Security.Authenticated(Secured.class)
	public Result listFunctionalities(String code) {
		Device device = deviceDao.findByFieldWith("code", code, "functionalities");

		return ok(device.getFunctionalities());
	}
}
