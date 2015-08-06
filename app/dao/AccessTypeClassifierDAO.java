package dao;

import models.AccessTypeClassifier;
import models.EnvironmentType;
import models.UserProfileEnvironment;

public interface AccessTypeClassifierDAO extends BaseDAO<AccessTypeClassifier, Integer> {
	public AccessTypeClassifier find(UserProfileEnvironment userProfile, EnvironmentType environmentType,
			char shift, int weekday, char workday);
}
