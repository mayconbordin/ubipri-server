package controllers;

import javax.inject.Inject;

import dao.UserDAO;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	@Inject UserDAO dao;

    public Result index() {
        return ok("Your new application is ready.");
    }

}
