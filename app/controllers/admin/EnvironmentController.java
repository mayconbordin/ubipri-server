package controllers.admin;

import com.avaje.ebean.PagedList;
import dao.EnvironmentDAO;
import models.Environment;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.environment.index;
import views.html.environment.edit;

import javax.inject.Inject;
import java.util.List;

public class EnvironmentController extends Controller {

    @Inject EnvironmentDAO dao;

    public Result index(int page) {
        int realPage = page - 1; // 0-based

        PagedList<Environment> pagedList = dao.findAllWith(realPage, 15, "environmentType", "localizationType");

        return ok(index.render(pagedList.getList(), page, pagedList.getTotalPageCount(), pagedList.getTotalRowCount()));
    }

    public Result edit(int id) {
        Environment e = dao.find(id);

        if (e == null) {
            return notFound();
        }

        return ok(edit.render(Form.form(Environment.class).fill(e)));
    }

}
