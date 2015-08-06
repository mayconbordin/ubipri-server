package dao;

import models.UserCredential;

public interface UserCredentialDAO extends BaseDAO<UserCredential, Integer> {
	public UserCredential findByUserIdAndSystem(String externalId, String system);
}
