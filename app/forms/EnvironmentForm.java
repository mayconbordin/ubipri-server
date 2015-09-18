package forms;

import models.Environment;
import models.EnvironmentType;
import models.LocalizationType;
import org.postgis.Point;
import org.postgis.Polygon;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import validators.PolygonWKT;

import java.sql.SQLException;

/**
 * Created by mayconbordin on 16/09/15.
 */
public class EnvironmentForm {
    private static final Logger.ALogger LOGGER = Logger.of(EnvironmentForm.class);

    @Constraints.Required
    @Constraints.MaxLength(100)
    private String name;

    @Constraints.Required
    private Integer localizationTypeId;

    @Constraints.Required
    private Integer environmentTypeId;

    @PolygonWKT
    private String shape;

    @Constraints.Required
    private double latitude;

    @Constraints.Required
    private double longitude;

    @Constraints.Required
    private double radius;

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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public static Form<EnvironmentForm> create(Environment e) {
        EnvironmentForm form = new EnvironmentForm();

        form.name = e.getName();
        form.localizationTypeId = e.getLocalizationType().getId();
        form.environmentTypeId = e.getEnvironmentType().getId();
        form.shape = e.getShape().toString();
        form.latitude = e.getLocation().getX();
        form.longitude = e.getLocation().getY();
        form.radius = e.getOperatingRange();

        return Form.form(EnvironmentForm.class).fill(form);
    }

    public void fill(Environment e) {
        try {
            e.setName(name);
            e.setShape(new Polygon(shape));
            e.setLocation(new Point(latitude, longitude, 0.0));
            e.setOperatingRange(radius);
            e.setEnvironmentType(new EnvironmentType(environmentTypeId));
            e.setLocalizationType(new LocalizationType(localizationTypeId));
        } catch (SQLException ex) {
            LOGGER.error("Unable to parse Polygon shape.");
        }
    }
}
