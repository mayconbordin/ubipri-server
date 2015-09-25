package services.auth;

import dao.OAuthAccessTokenDAO;
import models.OAuthAccessToken;
import models.User;
import oauth2.OAuth2DataHandler;
import play.mvc.Http;
import scala.Option;
import scala.Some;
import scalaoauth2.provider.AccessToken;
import scalaoauth2.provider.AuthInfo;

import javax.inject.Inject;


public class UbiPriAuthenticator implements Authenticator {
    private long accessTokenExpireSeconds = 3600;

    @Inject OAuthAccessTokenDAO accessTokenDAO;

    @Override
    public User getUser(Http.Request request) {
        String[] headers = request.headers().get("Authorization");

        if ((headers != null) && (headers.length == 1) && (headers[0] != null)) {
            String[] header = headers[0].split("\\s+");

            if (header.length == 2 && header[0].equals("Bearer")) {
                OAuthAccessToken token = accessTokenDAO.findByAccessToken(header[1]);

                if (token != null && !isTokenExpired(token)) {
                    return token.getAccount();
                }
            }
        }

        return null;
    }

    private boolean isTokenExpired(OAuthAccessToken accessToken) {
        return ((accessToken.getCreatedAt().getTime() + accessTokenExpireSeconds * 1000) <= System.currentTimeMillis());
    }
}
