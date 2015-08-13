package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import dao.AccessLevelDAO;
import dao.AccessTypeClassifierDAO;
import dao.ActionDAO;
import dao.DeviceDAO;
import dao.DeviceTypeDAO;
import dao.EnvironmentFrequencyLevelDAO;
import dao.LogEventDAO;
import dao.UserDAO;
import dao.UserEnvironmentDAO;
import play.Logger;
import models.AccessLevel;
import models.AccessTypeClassifier;
import models.Action;
import models.Device;
import models.Environment;
import models.EnvironmentFrequencyLevel;
import models.Functionality;
import models.LogEvent;
import models.User;
import models.UserEnvironment;
import models.UserEnvironmentPK;
import forms.UserLocationForm;

public class PrivacyService implements IPrivacyService {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Inject UserDAO userDao;
	@Inject DeviceDAO deviceDao;
	@Inject DeviceTypeDAO deviceTypeDao;
	@Inject UserEnvironmentDAO userEnvDao;
	@Inject LogEventDAO logDao;
	@Inject AccessTypeClassifierDAO classifierDao;
	@Inject AccessLevelDAO accessLevelDao;
	@Inject ActionDAO actionDao;
	@Inject EnvironmentFrequencyLevelDAO envFreqDao;
	
	public List<Action> changeUserLocation(User user, UserLocationForm form) {
		// get user information
		user = userDao.findWith(user.getId(), "environments", "userType");
		
		// get the device with its functionalities
		Device device = deviceDao.findByFieldWith("code", form.getDeviceCode(), "functionalities");
		
		// get the rules for the user in the current environment
		UserEnvironment userEnv = userEnvDao.findWith(new UserEnvironmentPK(
				user.getId(), form.getEnvironmentId()), "environment", "userProfile", 
				"currentAccessType", "environment.environmentType");
		
		Environment env = userEnv.getEnvironment();
		
		// Check if user/device has changed environments
		boolean hasChangedUserLocation   = (user.getCurrentEnvironment() == null 
				|| user.getCurrentEnvironment().getId() != form.getEnvironmentId());
		boolean hasChangedDeviceLocation = (device.getCurrentEnvironment() == null 
				|| device.getCurrentEnvironment().getId() != form.getEnvironmentId());
		
		// Update current user location
		if (hasChangedUserLocation) {
			if (form.isExiting()) {
				user.setCurrentEnvironment(null);
			} else {
				user.setCurrentEnvironment(env);
				
				//TODO: recalculate user frequency on the environment
			}
			
			userDao.update(user);
		}
		
		// Update current device location
		if (hasChangedDeviceLocation) {
			if (form.isExiting()) {
				device.setCurrentEnvironment(null);
			} else {
				device.setCurrentEnvironment(env);
			}
			
			deviceDao.update(device);
		}
		
		// create the log event
		LogEvent log = createLogEvent(user, env, device, form.isExiting());
		
		// Get the type of access based on 5 parameters
		AccessTypeClassifier classified = classifierDao.find(userEnv.getUserProfile(),
				env.getEnvironmentType(), log.getShift(), log.getWeekday(), log.getWorkday());

		if (classified == null) {
			//TODO: default actions
			return null;
		}
		
		// Find the user's access level
		AccessLevel accessLevel = accessLevelDao.findWith(env.getEnvironmentType(),
				classified.getAccessType(), "actions", "actions.functionality");

		List<Action> customActions = actionDao.getCustomActions(env, accessLevel);
		
		logger.info("AccessLevel actions: " + accessLevel.getActions().size());
		logger.info("Custom actions: " + customActions.size());
		logger.info("Device functionalities: " + device.getFunctionalities().size());
		
		return mergeActions(accessLevel.getActions(), customActions, device.getFunctionalities());
	}
	
	public LogEvent createLogEvent(User user, Environment environment, Device device, boolean exiting) {
		DateTime ts = DateTime.now();
		
		LogEvent log = new LogEvent();
		log.setUser(user);
		log.setEnvironment(environment);
		log.setDevice(device);
		log.setTime(ts.toDate());
		log.setCode("code");
		log.setEvent((exiting) ? "Leaving the location" : "Entering the location");
		
		if (ts.getDayOfWeek() <= 5) {
			log.setWeekday(LogEvent.DAY_OF_WEEK);
			log.setWorkday(LogEvent.YES_WORKDAY);
		} else {
			log.setWeekday(LogEvent.DAY_OF_WEEKEND);
			log.setWorkday(LogEvent.NOT_WORKDAY);
		}
		
		if (ts.getHourOfDay() <= 18) {
			log.setShift(LogEvent.DAY_SHIFT);
		} else {
			log.setShift(LogEvent.NIGHT_SHIFT);
		}
		
		logDao.create(log);
		
		return log;
	}
	
	public void updateUserFrequency(User user, Environment environment) {
		// get rules for frequency levels for the environment
		List<EnvironmentFrequencyLevel> levels = envFreqDao.findAllByField("environment", environment);
		
		// if not rules can be found, assume that users don't evolve in this environment
		if (levels.isEmpty()) {
			return;
		}

		// get the period for checking the user frequency
		Period period = getPeriodFromFrequencyLevels(levels);
		
		// get the actual frequency in days for the user in the period
		int frequencyInDays = logDao.count(user, environment, period);
		
		// calculate the presence of the user in the period
		double presence = (frequencyInDays*100)/period.getDays();
		

		System.out.println("LEVELS: " + levels.size());
		System.out.println("PERIOD: " + period.getDays() + " days");
		System.out.println("PRESENCE: " + presence + "%");
	}
	
	protected Period getPeriodFromFrequencyLevels(List<EnvironmentFrequencyLevel> levels) {
		EnvironmentFrequencyLevel firstLevel = levels.get(0);
		
		if (firstLevel.getMetric() == 'd') {
			return Period.days(firstLevel.getPeriod());
		} else if (firstLevel.getMetric() == 'w') {
			return Period.weeks(firstLevel.getPeriod());
		} else if (firstLevel.getMetric() == 'm') {
			return Period.months(firstLevel.getPeriod());
		} else if (firstLevel.getMetric() == 'y') {
			return Period.years(firstLevel.getPeriod());
		}
				
		return null;
	}
	
	protected List<Action> mergeActions(List<Action> accessLevelActions, List<Action> customActions,
			List<Functionality> deviceFunctionalities) {
		List<Action> finalActions = new ArrayList<Action>();
		
		// Override the default actions by the custom ones
        if (customActions.size() > 0) {
            for (int i = 0; i < accessLevelActions.size(); i++) {
                for (int j = 0; j < customActions.size(); j++) {
                    if (accessLevelActions.get(i).getId() == customActions.get(j).getId()) {
                    	accessLevelActions.set(i, customActions.get(j));
                    }
                }
            }
        }

        // Filter the actions by the number of functionalities of the device
        for (int i = 0; i < accessLevelActions.size(); i++) {
            for (int j = 0; j < deviceFunctionalities.size(); j++) {
                if (deviceFunctionalities.get(j).getId() == accessLevelActions.get(i).getFunctionality().getId()) {
                    finalActions.add(accessLevelActions.get(i));
                }
            }
        }
        
        return finalActions;
	}
}
