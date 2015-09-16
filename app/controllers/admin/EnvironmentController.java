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
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.environment.index;
import views.html.environment.edit;

import javax.inject.Inject;
import java.util.List;

public class EnvironmentController extends Controller {

    @Inject EnvironmentDAO dao;
    @Inject EnvironmentTypeDAO environmentTypeDAO;
    @Inject LocalizationTypeDAO localizationTypeDAO;

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

        List<EnvironmentType> types = environmentTypeDAO.findAll();
        List<LocalizationType> localizationTypes = localizationTypeDAO.findAll();
        Form<EnvironmentForm> form = Form.form(EnvironmentForm.class).fill(EnvironmentForm.create(e));

        return ok(edit.render(form, types, localizationTypes));
    }

}
