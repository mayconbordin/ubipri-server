package services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mayconbordin.oauth2.client.AccessToken;
import org.mayconbordin.oauth2.client.OAuth2Client;
import org.mayconbordin.oauth2.client.OAuth2Exception;

import play.Play;
import play.libs.Json;
import play.Configuration;
import play.Logger;

public class SigaiService implements ISigaiService {
	private final Logger.ALogger logger = Logger.of(this.getClass());
	private final Configuration config;
	
	private final String accessTokenUrl;
	private final String userInfoUrl;
	private final String clientId;
	private final String clientSecret;
	private final String scopes;
	
	private OAuth2Client client;
	private AccessToken accessToken;
		
	public SigaiService() {
		config = Play.application().configuration();
		
		accessTokenUrl = config.getString("sigai.access_token_url", "http://localhost/api/oauth/access_token");
		userInfoUrl    = config.getString("sigai.user_info_url", "http://localhost/api/usuario");
		clientId 	   = config.getString("sigai.client_id", "client2id");
		clientSecret   = config.getString("sigai.client_secret", "client2secret");
		scopes         = config.getString("sigai.scopes", "read-usuarios");
	}

	public boolean authenticateUser(String username, String password) {
		try {
			if (accessToken != null) {
				if (!accessToken.isExpired()) {
					return true;
				} else {
					accessToken = accessToken.refresh(client);
				}
			}
			
			if (username != null && password != null) {
				client = OAuth2Client.withPasswordGrant(username, password, 
						clientId, clientSecret, scopes, accessTokenUrl);
			}
			
			if (client != null) {
			    accessToken = client.getAccessToken();
			    return true;
			}
		} catch (OAuth2Exception e) {
			logger.error("Error in authentication of user with SIGA-i", e);
		}
		
		return false;
	}
	
	public Map<String, Object> getUserInfo() {
		try {
			if (accessToken == null || accessToken.isExpired()) {
				authenticateUser(null, null);
			}
			
		    String content = accessToken.getResource(userInfoUrl);
		    return parseResponse(content);
		} catch (OAuth2Exception e) {
			logger.error("Error getting the user information with SIGA-i", e);
		}
		
		return null;
	}
	
	protected Map<String, Object> parseResponse(String content) {
		try {
			return (Map<String, Object>) Json.mapper().readValue(content, HashMap.class);
		} catch (IOException e) {
			logger.error("Unable to parse response from SIGA-i", e);
		}
		
		return null;
	}
}
