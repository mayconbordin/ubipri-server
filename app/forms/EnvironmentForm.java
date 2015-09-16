package forms;

import models.Environment;
import play.data.validation.Constraints;

/**
 * Created by mayconbordin on 16/09/15.
 */
public class EnvironmentForm {
    @Constraints.Required
    @Constraints.MaxLength(100)
    private String name;

    @Constraints.Required
    private Integer localizationTypeId;

    @Constraints.Required
    private Integer environmentTypeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocalizationTypeId() {
        return localizationTypeId;
    }

    public void setLocalizationTypeId(Integer localizationTypeId) {
        this.localizationTypeId = localizationTypeId;
    }

    public Integer getEnvironmentTypeId() {
        return environmentTypeId;
    }

    public void setEnvironmentTypeId(Integer environmentTypeId) {
        this.environmentTypeId = environmentTypeId;
    }

    public static EnvironmentForm create(Environment e) {
        EnvironmentForm form = new EnvironmentForm();

        form.name = e.getName();
        form.localizationTypeId = e.getLocalizationType().getId();
        form.environmentTypeId = e.getEnvironmentType().getId();

        return form;
    }
}
