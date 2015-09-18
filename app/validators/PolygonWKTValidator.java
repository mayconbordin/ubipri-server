package validators;

import org.postgis.Polygon;
import play.libs.F;

import javax.validation.*;
import play.data.validation.Constraints.Validator;

import java.sql.SQLException;

public class PolygonWKTValidator extends Validator<Object> implements ConstraintValidator<PolygonWKT, Object> {
    /* Default error message */
    final static public String message = "error.polygonwkt";

    /**
     * Validator init
     * Can be used to initialize the validation based on parameters
     * passed to the annotation.
     */
    public void initialize(PolygonWKT constraintAnnotation) {}

    /**
     * The validation itself
     */
    public boolean isValid(Object object) {
        if(object == null)
            return false;

        if(!(object instanceof String))
            return false;

        try {
            Polygon p = new Polygon(object.toString());
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple(message, new Object[] {});
    }

    /**
     * Constructs a validator instance.
     */
    public static play.data.validation.Constraints.Validator<Object> polygonwkt() {
        return new PolygonWKTValidator();
    }
}
