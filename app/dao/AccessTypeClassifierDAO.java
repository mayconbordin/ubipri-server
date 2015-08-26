package dao;

import models.*;

public interface AccessTypeClassifierDAO extends BaseDAO<AccessTypeClassifier, Integer> {
	public AccessTypeClassifier find(UserProfileEnvironment userProfile, EnvironmentType environmentType,
			char shift, int weekday, char workday, FrequencyLevel frequencyLevel);
}
