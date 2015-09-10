package services.auth;

import com.google.common.collect.ImmutableList;
import com.google.inject.name.Named;
import controllers.SecurityController;
import dao.UserCredentialDAO;
import dao.UserDAO;
import dao.UserTypeDAO;
import models.User;
import models.UserCredential;
import models.UserType;
import org.mayconbordin.oauth2.client.AccessToken;
import org.mayconbordin.oauth2.client.OAuth2Client;
import org.mayconbordin.oauth2.client.OAuth2Exception;
import play.Configuration;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SigaiAuthenticator implements Authenticator {
    public final static String NAME = "SIGA-i";
    private static final List<String> roles = ImmutableList.of("Aluno", "Professor", "Coordenador", "Administrador");

    private final Logger.ALogger logger = Logger.of(this.getClass());
    private final Configuration config;

    private final String accessTokenUrl;
    private final String verifyTokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scopes;

    private OAuth2Client client;
    private AccessToken accessToken;

    @Inject UserDAO userDao;
    @Inject UserTypeDAO userTypeDao;
    @Inject UserCredentialDAO credentialsDao;

    public SigaiAuthenticator() {
        config = Play.application().configuration();

        accessTokenUrl = config.getString("sigai.access_token_url", "http://localhost/api/oauth/access_token");
        verifyTokenUrl = config.getString("sigai.verify_token_url", "http://localhost/api/oauth/verify");
        clientId 	   = config.getString("sigai.client_id", "ubipri");
        clientSecret   = config.getString("sigai.client_secret", "163a53cf0ed6f83b564cbe3497eb95436229e1c0");
        scopes         = config.getString("sigai.scopes", "verify-token");
    }

    @Override
    public User getUser(Http.Request request) {
        User user = null;
        String[] headers = request.headers().get("Authorization");

        if ((headers != null) && (headers.length == 1) && (headers[0] != null)) {
            String[] header = headers[0].split("\\s+");

            if (header.length == 2 && header[0].equals("Bearer")) {
                Map<String, Object> data = verifyToken(header[1]);

                if (((boolean) data.get("valid")) == true) {
                    Map<String, Object> userInfo = (Map<String, Object>) data.get("user");
                    UserCredential credential = credentialsDao.findByUserIdAndSystem((String) userInfo.get("matricula"), NAME);

                    if (credential != null) {
                        return credential.getUser();
                    } else {
                        return register(userInfo);
                    }
                }
            }
        }

        return user;
    }

    public Map<String, Object> verifyToken(String token) {
        try {
            String content = getAccessToken().getResource(verifyTokenUrl+"?token="+token);
            return parseResponse(content);
        } catch (OAuth2Exception e) {
            logger.error("Error in authentication of user with SIGA-i", e);
        }

        return null;
    }

    public User register(Map<String, Object> userInfo) {
        String matricula = (String) userInfo.get("matricula");
        String fullName = (String) userInfo.get("nome");
        String[] nameSplit = fullName.split("\\s+");

        User user = new User();
        user.setName(nameSplit.length > 0 ? nameSplit[0].toLowerCase() : "");
        user.setFullName(fullName);
        user.setEmailAddress((String) userInfo.get("email"));
        user.setUserType(parseUserTypeFromRoles((List<Map<String, Object>>) userInfo.get("roles")));

        // create user
        userDao.create(user);

        UserCredential credential = new UserCredential();
        credential.setUser(user);
        credential.setExternalId(matricula);
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
        
        if (selectedRole == null || listOfRoles.size() == 0) {
            selectedRole = "Normal";
        }

        return userTypeDao.findByField("name", selectedRole);
    }

    protected Map<String, Object> parseResponse(String content) {
        try {
            return (Map<String, Object>) Json.mapper().readValue(content, HashMap.class);
        } catch (IOException e) {
            logger.error("Unable to parse response from SIGA-i", e);
        }

        return null;
    }

    public AccessToken getAccessToken() throws OAuth2Exception {
        if (accessToken == null || accessToken.isExpired()) {
            accessToken = getOAuthClient().getAccessToken();
        }
        return accessToken;
    }

    public OAuth2Client getOAuthClient() {
        if (client == null) {
            client = OAuth2Client.withClientCredentialsGrant(clientId, clientSecret, scopes, accessTokenUrl);
        }

        return client;
    }

    public void setClient(OAuth2Client client) {
        this.client = client;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
