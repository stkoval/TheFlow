package validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Stas
 */
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ProjectAliasValidator.class)
@Documented
public @interface ValidProjectAlias {

    String message() default "Invalid project alias. Must start with letter. Only letters in lower case and numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
