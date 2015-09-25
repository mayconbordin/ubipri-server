package dao;

import models.OAuthClient;
import models.User;

public interface OAuthClientDAO extends BaseDAO<OAuthClient, Integer> {
	public boolean validate(String clientId, String clientSecret, String grantType);
	public OAuthClient findByClientId(String clientId);
	public User findClientCredentials(String clientId, String clientSecret);
}
