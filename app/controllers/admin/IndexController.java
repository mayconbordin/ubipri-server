package controllers.admin;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class IndexController extends Controller {

    public Result index() {
        return ok(index.render());
    }

}
