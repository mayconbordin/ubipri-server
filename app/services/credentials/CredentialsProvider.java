package services.credentials;

import java.util.Map;

import models.User;

public interface CredentialsProvider {
	/**
	 * Authenticate an user based on its user id (name, email, etc) and password.
	 * 
	 * @param userId
	 * @param password
	 * @return An user object if successfully authenticated or null otherwise.
	 */
	public User authenticate(String userId, String password);
	
	/**
	 * Register a new user on the system based on already existing credentials from
	 * the credentials provider.
	 * 
	 * @param userId
	 * @param password
	 * @param data Extra data necessary for the registration.
	 * @return The registered user.
	 */
	public User register(String userId, String password, Map<String, String> data);
}
