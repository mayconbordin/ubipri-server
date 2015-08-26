package base;

import dao.UserDAO;
import models.User;
import play.mvc.Http;
import services.auth.Authenticator;

import javax.inject.Inject;

/**
 * Created by mayconbordin on 26/08/15.
 */
public class MockAuthenticator implements Authenticator {
    @Inject UserDAO userDAO;

    @Override
    public User getUser(Http.Request request) {
        return userDAO.find(1);
    }
}
