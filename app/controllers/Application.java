package controllers;

import javax.inject.Inject;

import dao.ebean.UserEbeanDAO;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok("Your new application is ready.");
    }

}
