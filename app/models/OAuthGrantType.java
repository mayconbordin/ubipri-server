package models;

/**
 * Created by mayconbordin on 24/09/15.
 */
public interface OAuthGrantType {
    String PASSWORD = "password";
    String CLIENT_CREDENTIALS = "client_credentials";
    String REFRESH_TOKEN = "refresh_token";
    String AUTHORIZATION_CODE = "authorization_code";
}
