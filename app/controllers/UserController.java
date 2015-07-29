package controllers;

import java.util.List;

import javax.inject.Inject;

import dao.DeviceDAO;
import dao.DeviceTypeDAO;
import dao.UserDAO;
import dao.UserEnvironmentDAO;
import forms.UserDeviceForm;
import forms.UserLocationForm;
import models.Device;
import models.DeviceType;
import models.User;
import models.UserEnvironment;
import models.UserEnvironmentPK;
import models.serialization.Views;
import play.data.Form;
import play.mvc.*;
import services.PrivacyService;
import utils.http.BaseController;
import utils.security.Secured;

public class UserController extends BaseController {
	@Inject UserDAO userDao;
	@Inject DeviceDAO deviceDao;
	@Inject DeviceTypeDAO deviceTypeDao;
	@Inject UserEnvironmentDAO userEnvDao;
	
	@Inject PrivacyService service;
	
	@Security.Authenticated(Secured.class)
	public Result getUser() {
		User user = getAuthUser();
		return ok(user);
	}
	
	@Security.Authenticated(Secured.class)
	public Result updateUser() {
		User user = getAuthUser();
		
		Form<User> form = Form.form(User.class).bindFromRequest();

		if (form.hasErrors()) {
			return invalidForm(form);
		}

		user.setName(form.get().getName());
		user.setEmailAddress(form.get().getEmailAddress());
		user.setFullName(form.get().getFullName());
		user.setPassword(form.get().getPassword());
		
		userDao.update(user);
		
		return ok(user);
	}
	
	@Security.Authenticated(Secured.class)
	public Result listDevices() {
		User user = getAuthUser();
		
		List<Device> devices = user.getDevices();
		
		return ok(devices);
	}
	
	@Security.Authenticated(Secured.class)
	public Result addDevice() {
		User user = getAuthUser();
		
		Form<UserDeviceForm> form = Form.form(UserDeviceForm.class).bindFromRequest();
		
		if (form.hasErrors()) {
			return invalidForm(form);
		}
		
		Device device = new Device();
		device.setCode(form.get().getCode());
		device.setName(form.get().getName());
		device.setUser(user);
		
		DeviceType type = deviceTypeDao.findByField("name", form.get().getDeviceType());
		
		if (type == null) {
			return invalidField("deviceType", "Device type not found");
		}
		
		device.setType(type);
		deviceDao.create(device);
		
		return created(device);
	}
	
	@Security.Authenticated(Secured.class)
	public Result updateLocation() {
		Form<UserLocationForm> form = Form.form(UserLocationForm.class).bindFromRequest();
		
		if (form.hasErrors()) {
			return invalidForm(form);
		}
		
		List<models.Action> actions = service.changeUserLocation(getAuthUser(), form.get());
		
		return ok(actions, Views.Basic.class);
	}
}
