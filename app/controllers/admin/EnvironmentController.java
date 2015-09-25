package controllers.admin;

import com.avaje.ebean.PagedList;
import com.google.common.collect.ImmutableMap;
import dao.EnvironmentDAO;
import dao.EnvironmentTypeDAO;
import dao.LocalizationTypeDAO;
import forms.EnvironmentForm;
import models.Environment;
import models.EnvironmentType;
import models.LocalizationType;
import org.postgis.Polygon;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.http.BaseController;
import views.html.environment.index;
import views.html.environment.edit;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class EnvironmentController extends BaseController {

    @Inject EnvironmentDAO dao;
    @Inject EnvironmentTypeDAO environmentTypeDAO;
    @Inject LocalizationTypeDAO localizationTypeDAO;

    public Result index(int page) {
        int realPage = page - 1; // 0-based

        PagedList<Environment> pagedList = dao.findAllWith(realPage, 15, "environmentType", "localizationType");

        return ok(index.render(pagedList.getList(), page, pagedList.getTotalPageCount(), pagedList.getTotalRowCount()));
    }

    public Result show(int id) {
        Environment e = dao.find(id);

        if (e == null) {
            return notFound();
        }

        List<EnvironmentType> eTypes  = environmentTypeDAO.findAll();
        List<LocalizationType> lTypes = localizationTypeDAO.findAll();
        Form<EnvironmentForm> form    = EnvironmentForm.create(e);

        return ok(edit.render(form, eTypes, lTypes));
    }

    public Result edit(int id) {
        List<EnvironmentType> eTypes  = environmentTypeDAO.findAll();
        List<LocalizationType> lTypes = localizationTypeDAO.findAll();
        Form<EnvironmentForm> form    = Form.form(EnvironmentForm.class).bindFromRequest();

        if (form.hasErrors()) {
            flash("error", "There are errors in the form");
            return badRequest(edit.render(form, eTypes, lTypes));
        }

        Environment e = dao.find(id);

        if (e == null) {
            return notFound();
        }

        // fill Environment object with new values
        form.get().fill(e);

        // Check if environment was updated
        if (dao.update(e) == null) {
            flash("error", "Unable to save Environment.");
        } else {
            flash("success", "Environment successfully saved.");
        }

        return redirect(routes.EnvironmentController.show(1));
    }

}
