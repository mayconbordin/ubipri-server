package services.auth;

import models.User;
import play.mvc.Http;

public interface Authenticator {
    public User getUser(Http.Request request);
}
