package services.credentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import dao.UserCredentialDAO;
import dao.UserDAO;
import dao.UserTypeDAO;
import models.User;
import models.UserCredential;
import models.UserType;
import play.libs.Json;
import services.ISigaiService;

public class SigaiCredentialsProvider implements CredentialsProvider {
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
		
		String userInfo = service.getUserInfo();

		// get user information
		JsonNode json = Json.parse(userInfo);
		String fullName = json.has("nome") ? json.get("nome").textValue() : "";
		String[] nameSplit = fullName.split("\\s+");

		User user = new User();
		user.setName(nameSplit.length > 0 ? nameSplit[0].toLowerCase() : "");
		user.setFullName(fullName);
		user.setEmailAddress(json.has("email") ? json.get("email").textValue() : "");
		user.setPassword(password);
		user.setUserType(parseUserTypeFromRoles(json));
		
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
	
	protected UserType parseUserTypeFromRoles(JsonNode json) {
		List<String> userRoles = new ArrayList<String>();
		Iterator<JsonNode> it = json.get("roles").iterator();
		
		while (it.hasNext()) {
			JsonNode role = it.next();
			userRoles.add(role.get("display_name").asText().trim());
		}
		
		String selectedRole = null;
		
		for (String role : roles) {
			if (userRoles.contains(role)) {
				selectedRole = role;
			}
		}
		
		return userTypeDao.findByField("name", selectedRole);
	}
}
