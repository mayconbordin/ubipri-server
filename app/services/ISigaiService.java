package services;

import java.util.Map;

public interface ISigaiService {
	public boolean authenticateUser(String username, String password);
	public Map<String, Object> getUserInfo();
}
