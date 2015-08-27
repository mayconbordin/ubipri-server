package services;

import java.util.List;

import forms.UserLocationForm;
import models.*;
import org.joda.time.DateTime;

public interface IPrivacyService {
	public List<Action> changeUserLocation(User user, UserLocationForm form);
	public LogEvent createLogEvent(User user, Environment environment, Device device, boolean exiting);
	public FrequencyLevel getFrequencyLevel(User user, Environment environment, DateTime date);
	public EnvironmentFrequencyLevel getUserFrequencyLevel(User user, Environment environment, DateTime date);
	public UserProfileEnvironment updateUserProfileInEnvironment(UserEnvironment userInEnvironment, FrequencyLevel frequencyLevel);
}
