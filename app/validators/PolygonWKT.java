package validators;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.*;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PolygonWKTValidator.class)
@play.data.Form.Display(name="constraint.polygonwkt")
public @interface PolygonWKT {
    String message() default PolygonWKTValidator.message;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
