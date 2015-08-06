package dao;

import models.AccessLevel;
import models.AccessType;
import models.EnvironmentType;

public interface AccessLevelDAO extends BaseDAO<AccessLevel, Integer> {
	public AccessLevel find(EnvironmentType environmentType, AccessType accessType);
	public AccessLevel findWith(EnvironmentType environmentType, AccessType accessType, String...relations);
}
