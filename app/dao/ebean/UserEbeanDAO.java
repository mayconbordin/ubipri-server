package dao.ebean;

import utils.HashUtil;
import dao.UserDAO;
import models.User;

public class UserEbeanDAO extends BaseEbeanDAO<User, Integer> implements UserDAO {

	@Override
	public User findByName(String name) {
		return createQuery().where().eq("name", name).findUnique();
	}

	@Override
	public User findByAuthToken(String authToken) {
		if (authToken == null) {
			return null;
		}
		
		return createQuery().where().eq("authToken", authToken).findUnique();
	}

	@Override
	public User findByEmailAddressAndPassword(String emailAddress, String password) {
		return createQuery().where().eq("emailAddress", emailAddress.toLowerCase())
				.eq("shaPassword", HashUtil.getSha512(password)).findUnique();
	}

	@Override
	public User findByNameAndPassword(String name, String password) {
		User user = createQuery().where().eq("name", name).findUnique();;

		if (user != null && HashUtil.checkPassword(password, user.getPassword())) {
			return user;
		}

		return null;
	}
}
