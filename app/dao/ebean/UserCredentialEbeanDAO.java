package dao.ebean;

import dao.UserCredentialDAO;
import models.User;
import models.UserCredential;

public class UserCredentialEbeanDAO extends BaseEbeanDAO<UserCredential, Integer> implements UserCredentialDAO {
	public UserCredential findByUserIdAndSystem(String externalId, String system) {
		return createQuery()
				.fetch("user")
				.where().eq("externalId", externalId)
				.where().eq("system", system)
				.findUnique();
	}
}
