package dao;

import models.OAuthAccessToken;
import models.User;

public interface OAuthAccessTokenDAO extends BaseDAO<OAuthAccessToken, Integer> {
	public OAuthAccessToken findByAccessToken(String accessToken);
	public OAuthAccessToken findByAuthorized(User user, String clientId);
	public OAuthAccessToken findByRefreshToken(String refreshToken);
	public void deleteByAuthorized(User user, String clientId);
}
