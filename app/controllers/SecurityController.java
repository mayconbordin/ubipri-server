package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import models.User;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import utils.http.BaseController;
import utils.security.Secured;
import dao.UserDAO;
import forms.LoginForm;

public class SecurityController extends BaseController {
	public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
	public static final String AUTH_TOKEN = "authToken";
	
	@Inject UserDAO userDao;

	public Result login() {
		Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
		
		if (loginForm.hasErrors()) {
			return badRequest(loginForm);
		}
		
		LoginForm login = loginForm.get();
		
		User user = userDao.findByEmailAddressAndPassword(login.getEmailAddress(),
				login.getPassword());
		
		if (user == null) {
			return unauthorized(message("Unauthorized access", 1));
		} else {
			String authToken = user.createToken();
			userDao.update(user);
			
			response().setCookie(AUTH_TOKEN, authToken);
			
			Map<String, String> data = new HashMap<String, String>();
			data.put(AUTH_TOKEN, authToken);

			return ok(data);
		}
	}
	
	@Security.Authenticated(Secured.class)
	public Result logout() {
		response().discardCookie(AUTH_TOKEN);
		User user = getAuthUser();
		user.setAuthToken(null);
		userDao.update(user);
		return ok();
	}

}