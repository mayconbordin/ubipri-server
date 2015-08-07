package services.credentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;

import dao.UserCredentialDAO;
import dao.UserDAO;
import dao.UserTypeDAO;
import models.User;
import models.UserCredential;
import models.UserType;
import play.Logger;
import services.ISigaiService;

public class SigaiCredentialsProvider implements CredentialsProvider {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	public final static String NAME = "SIGA-i";
	
	@Inject ISigaiService service;
	@Inject UserDAO userDao;
	@Inject UserTypeDAO userTypeDao;
	@Inject UserCredentialDAO credentialsDao;
	
	private static final List<String> roles = ImmutableList.of("Aluno", "Professor", "Coordenador", "Administrador");

	@Override
	public User authenticate(String userId, String password) {
		if (!service.authenticateUser(userId, password)) {
			return null;
		}
		
		UserCredential credential = credentialsDao.findByUserIdAndSystem(userId, NAME);
		
		if (credential != null) {
			return credential.getUser();
		}
		
		return null;
	}

	@Override
	public User register(String userId, String password, Map<String, String> data) {
		if (!service.authenticateUser(userId, password)) {
			return null;
		}
		
		Map<String, Object> userInfo = service.getUserInfo();

		// get user information
		//JsonNode json = Json.parse(userInfo);
		String fullName = (String) userInfo.get("nome");
		String[] nameSplit = fullName.split("\\s+");

		User user = new User();
		user.setName(nameSplit.length > 0 ? nameSplit[0].toLowerCase() : "");
		user.setFullName(fullName);
		user.setEmailAddress((String) userInfo.get("email"));
		user.setPassword(password);
		user.setUserType(parseUserTypeFromRoles((List<Map<String, Object>>) userInfo.get("roles")));
		
		// create user
		userDao.create(user);
		
		UserCredential credential = new UserCredential();
		credential.setUser(user);
		credential.setExternalId(userId);
		credential.setSystem(NAME);
		
		// create user credentials
		credentialsDao.create(credential);
		
		user.setCredentials(ImmutableList.of(credential));
		
		return user;
	}
	
	protected UserType parseUserTypeFromRoles(List<Map<String, Object>> userRoles) {
		List<String> listOfRoles = new ArrayList<String>();
		
		for (Map<String, Object> userRole : userRoles) {
			listOfRoles.add((String) userRole.get("display_name"));
		}
		
		String selectedRole = null;

		for (String role : roles) {
			if (listOfRoles.contains(role)) {
				selectedRole = role;
			}
		}
		
		return userTypeDao.findByField("name", selectedRole);
	}
}
