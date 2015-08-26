package acceptance;

import org.mayconbordin.oauth2.client.AccessToken;
import org.mayconbordin.oauth2.client.OAuth2Client;
import org.mayconbordin.oauth2.client.OAuth2Exception;
import play.Logger;

import static acceptance.OAuthConstants.*;

public class OAuthClient {
    private static final Logger.ALogger LOGGER = Logger.of("OAuthClient");
    private static OAuth2Client client;

    public static String getAccessToken() {
        try {
            AccessToken token = getClient().getAccessToken();
            return token.getAccessToken();
        } catch (OAuth2Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static OAuth2Client getClient() {
        if (client == null) {
            client = OAuth2Client.withPasswordGrant(USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN_URL);
        }

        return client;
    }
}
