package dao;

import models.User;

public interface UserDAO extends BaseDAO<User, Integer> {
	public User findByName(String name);
	public User findByAuthToken(String authToken);
	public User findByEmailAddressAndPassword(String emailAddress, String password);
}
