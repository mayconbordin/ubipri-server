package services;

public interface ISigaiService {
	public boolean authenticateUser(String username, String password);
	public String getUserInfo();
}
