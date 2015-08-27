package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;
import dao.*;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.Period;

import play.Logger;
import forms.UserLocationForm;

public class PrivacyService implements IPrivacyService {
	private final Logger.ALogger logger = Logger.of(this.getClass());

	private static final List<Integer> evolvableProfiles = ImmutableList.of(
			UserProfileEnvironment.UNKNOW, UserProfileEnvironment.TRANSIENT, UserProfileEnvironment.USER
	);
	
	@Inject UserDAO userDao;
	@Inject DeviceDAO deviceDao;
	@Inject DeviceTypeDAO deviceTypeDao;
	@Inject UserEnvironmentDAO userEnvironmentDao;
	@Inject LogEventDAO logDao;
	@Inject AccessTypeClassifierDAO classifierDao;
	@Inject AccessLevelDAO accessLevelDao;
	@Inject ActionDAO actionDao;
	@Inject EnvironmentFrequencyLevelDAO envFreqDao;
	@Inject FrequencyLevelDAO frequencyLevelDao;
	@Inject UserProfileEnvironmentDAO userProfileEnvironmentDAO;

	@Inject IClock clock;
	
	public List<Action> changeUserLocation(User user, UserLocationForm form) {
		// get user information
		user = userDao.findWith(user.getId(), "environments", "userType");
		
		// get the device with its functionalities
		Device device = deviceDao.findByFieldWith("code", form.getDeviceCode(), "functionalities");
		
		// get the rules for the user in the current environment
		UserEnvironment userInEnvironment = userEnvironmentDao.findWith(new UserEnvironmentPK(
				user.getId(), form.getEnvironmentId()), "environment", "userProfile", 
				"currentAccessType", "environment.environmentType", "user");
		
		Environment environment = userInEnvironment.getEnvironment();

		// Update current user and device location
		updateUserAndDeviceLocation(user, device, environment, form.isExiting());
		
		// create the log event
		LogEvent log = createLogEvent(user, environment, device, form.isExiting());

		// If exiting environment, there is no need for getting its actions
		if (form.isExiting()) {
			return null;
		}

		// get user frequency on the environment
		FrequencyLevel frequencyLevel = getFrequencyLevel(user, environment, new DateTime(log.getTime()));

		// see if the user can be evolved in the environment
		UserProfileEnvironment userProfile = updateUserProfileInEnvironment(userInEnvironment, frequencyLevel);

		// Get the type of access based on 6 parameters
		AccessTypeClassifier classified = classifierDao.find(userProfile, environment.getEnvironmentType(), log.getShift(),
				log.getWeekday(), log.getWorkday(), frequencyLevel);

		// print classification information
		logger.info("user_profile="+userProfile.getName() + "; environment_type="+environment.getEnvironmentType().getName()
				+ "; shift="+log.getShift() + "; weekday=" + log.getWeekday() + "; workday=" + log.getWorkday()
				+ "; frequency_level=" + frequencyLevel + "; access_type=" + classified.getAccessType());

		if (classified == null) {
			return null;
		}
		
		// Find the user's access level based on the environment type and access type
		AccessLevel accessLevel = accessLevelDao.findWith(environment.getEnvironmentType(),
				classified.getAccessType(), "actions", "actions.functionality");

		// Get the custom actions (actions set for a particular environment)
		List<Action> customActions = actionDao.getCustomActions(environment, accessLevel);

		// logging info
		logger.info("AccessLevel "+accessLevel.getId()+" actions: " + accessLevel.getActions().size());
		logger.info("Custom actions: " + customActions.size());
		logger.info("Device functionalities: " + device.getFunctionalities().size());
		
		return mergeActions(accessLevel.getActions(), customActions, device.getFunctionalities());
	}
	
	public LogEvent createLogEvent(User user, Environment environment, Device device, boolean exiting) {
		DateTime ts = clock.getCurrentDateTime();
		
		LogEvent log = new LogEvent();
		log.setUser(user);
		log.setEnvironment(environment);
		log.setDevice(device);
		log.setTime(ts.toDate());
		log.setExiting(exiting);

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

	public FrequencyLevel getFrequencyLevel(User user, Environment environment, DateTime date) {
		EnvironmentFrequencyLevel envFreqLevel = getUserFrequencyLevel(user, environment, date);
		FrequencyLevel frequencyLevel = null;

		if (envFreqLevel == null) {
			frequencyLevel = frequencyLevelDao.findByField("name", FrequencyLevel.NORMAL);
		} else {
			frequencyLevel = envFreqLevel.getFrequencyLevel();
		}

		return frequencyLevel;
	}
	
	public EnvironmentFrequencyLevel getUserFrequencyLevel(User user, Environment environment, DateTime date) {
		// get rules for frequency levels for the environment
		List<EnvironmentFrequencyLevel> levels = envFreqDao.findAllByField("environment", environment);
		
		// if not rules can be found, assume that users don't evolve in this environment
		if (levels.isEmpty()) {
			return null;
		}

		// get the period for checking the user frequency
		Period period = getPeriodFromFrequencyLevels(levels);
		
		// get the actual frequency in days for the user in the period
		int frequencyInDays = logDao.count(user, environment, period, date);
		
		// calculate the percentage of presence of the user in the period
		double presence = (frequencyInDays*100)/period.getDays();

		// logging info
		logger.debug(String.format("User presence: %.2f%% (%d of %d days).", presence, frequencyInDays, period.getDays()));

		EnvironmentFrequencyLevel selectedLevel = null;

		for (EnvironmentFrequencyLevel l : levels) {
			if (presence >= l.getMin() && presence <= l.getMax()) {
				selectedLevel = l;
			}
		}

		return selectedLevel;
	}

	/**
	 * Get the period based on the first frequency level (assume all of them use the SAME metric the same for one environment).
	 *
	 * @param levels
	 * @return
	 */
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

	/**
	 * Override the access level actions with custom ones, if they exist, and remove those that are not applied to the current
	 * device.
	 *
	 * @param accessLevelActions
	 * @param customActions
	 * @param deviceFunctionalities
	 * @return
	 */
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

	/**
	 * Update the current user and device location on the database, if necessary.
	 *
	 * @param user
	 * @param device
	 * @param environment
	 * @param isExiting
	 */
	protected void updateUserAndDeviceLocation(User user, Device device, Environment environment, boolean isExiting) {
		boolean hasChangedUserLocation   = (user.getCurrentEnvironment() == null
				|| user.getCurrentEnvironment().getId() != environment.getId());
		boolean hasChangedDeviceLocation = (device.getCurrentEnvironment() == null
				|| device.getCurrentEnvironment().getId() != environment.getId());

		// Update current user location
		if (hasChangedUserLocation) {
			user.setCurrentEnvironment(isExiting ? null : environment);
			userDao.update(user);
		}

		// Update current device location
		if (hasChangedDeviceLocation) {
			device.setCurrentEnvironment(isExiting ? null : environment);
			deviceDao.update(device);
		}
	}

	public UserProfileEnvironment updateUserProfileInEnvironment(UserEnvironment userInEnvironment, FrequencyLevel frequencyLevel) {
		UserProfileEnvironment profile = userInEnvironment.getUserProfile();

		// check if the profile can evolve
		if (evolvableProfiles.contains(profile.getId())) {
			int index = evolvableProfiles.indexOf(profile.getId());

			// less frequent means we downgrade de user, if possible
			if (frequencyLevel.getName().equals(FrequencyLevel.LESS_FREQUENT)) {
				// can't downgrade more, just leave it like that
				if (index == 0) {
					return profile;
				}

				profile = userProfileEnvironmentDAO.find(evolvableProfiles.get(index-1));
			}

			// frequent means the user is going to be upgraded
			else if (frequencyLevel.getName().equals(FrequencyLevel.FREQUENT)) {
				// unless there's nowhere to go up
				if (index == (evolvableProfiles.size() - 1)) {
					return profile;
				}

				profile = userProfileEnvironmentDAO.find(evolvableProfiles.get(index+1));
			}

			// otherwise everything stays the same
			else {
				return profile;
			}

			// if got here means we need to update the profile of the user in the environment
			userInEnvironment.setUserProfile(profile);
			userEnvironmentDao.update(userInEnvironment);
		}

		return profile;
	}
}
