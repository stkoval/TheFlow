package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Stas
 */
public class ProjectAliasValidator implements ConstraintValidator<ValidProjectAlias, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PROJECT_ALIAS_PATTERN = "^([a-zа-я])+(_?\\d*([a-zа-я0-9])*)*([a-zа-я0-9]+)$";

    @Override
    public void initialize(ValidProjectAlias constraintAnnotation) {
    }

    @Override
    public boolean isValid(String projectAlias, ConstraintValidatorContext context) {
        return (validateProjectAlias(projectAlias));
    }

    private boolean validateProjectAlias(String companyAlias) {
        pattern = Pattern.compile(PROJECT_ALIAS_PATTERN);
        matcher = pattern.matcher(companyAlias);
        return matcher.matches();
    }
}
