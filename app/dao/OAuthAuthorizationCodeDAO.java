package dao;

import models.OAuthAuthorizationCode;

public interface OAuthAuthorizationCodeDAO extends BaseDAO<OAuthAuthorizationCode, Integer> {
	public OAuthAuthorizationCode findByCode(String code);
	public void deleteByCode(String code);
}
