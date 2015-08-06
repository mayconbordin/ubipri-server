package services.credentials;

import java.util.Map;

import javax.inject.Inject;

import dao.UserDAO;
import dao.UserTypeDAO;
import models.User;

public class DefaultCredentialsProvider implements CredentialsProvider {
	@Inject UserDAO userDao;
	@Inject UserTypeDAO userTypeDao;

	@Override
	public User authenticate(String userId, String password) {
		return userDao.findByEmailAddressAndPassword(userId, password);
	}

	@Override
	public User register(String userId, String password, Map<String, String> data) {
		User user = new User();
		user.setName(data.get("name"));
		user.setFullName(data.get("fullName"));
		user.setEmailAddress(data.get("email"));
		user.setPassword(data.get("password"));
		
		user.setUserType(userTypeDao.findByField("name", "Normal"));

		return userDao.create(user);
	}
}
