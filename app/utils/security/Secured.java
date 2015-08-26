package utils.security;

import controllers.SecurityController;
import dao.ebean.UserEbeanDAO;
import models.User;
import play.Play;
import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.Authenticator;
import utils.http.Errors;

import javax.inject.Inject;

public class Secured extends Security.Authenticator {
	
	private Authenticator authenticator;
	
	public Secured() {
		super();

		authenticator = Play.application().injector().instanceOf(Authenticator.class);
	}
	
	@Override
	public String getUsername(Context ctx) {
		User user = authenticator.getUser(ctx.request());

		if (user != null) {
			ctx.args.put("user", user);
			return user.getEmailAddress();
		}

		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		Errors error = Errors.create("Unauthorized access", 1);
		return unauthorized(Json.toJson(error));
	}
}