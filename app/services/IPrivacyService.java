package services;

import java.util.List;

import forms.UserLocationForm;
import models.Action;
import models.Device;
import models.Environment;
import models.LogEvent;
import models.User;

public interface IPrivacyService {
	public List<Action> changeUserLocation(User user, UserLocationForm form);
	public LogEvent createLogEvent(User user, Environment environment, Device device, boolean exiting);
	public void updateUserFrequency(User user, Environment environment);
}
