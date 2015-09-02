package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.*;
import forms.UserDeviceForm;
import forms.UserLocationForm;
import models.*;
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
	@Inject FunctionalityDAO functionalityDAO;
	
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

		// check if device code already exists
		if (deviceDao.existsCode(form.get().getCode())) {
			return conflict("Device code already exists.");
		}
		
		Device device = new Device();
		device.setCode(form.get().getCode());
		device.setName(form.get().getName());
		device.setUser(user);

		// find the type of device by name
		DeviceType type = deviceTypeDao.findByField("name", form.get().getDeviceType());
		if (type == null) {
			return invalidField("deviceType", "Device type not found");
		}
		
		device.setType(type);

		List<Functionality> functionalities = new ArrayList<>();
		for (String fName : form.get().getFunctionalities()) {
			Functionality f = functionalityDAO.findByField("name", fName);
			functionalities.add(f);
		}

		device.setFunctionalities(functionalities);

		deviceDao.create(device);
		
		return created(device, Views.Full.class);
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
