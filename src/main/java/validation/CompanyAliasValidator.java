package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Stas
 */
public class CompanyAliasValidator implements ConstraintValidator<ValidCompanyAlias, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String COMPANY_ALIAS_PATTERN = "^([a-zа-я])+(_?\\d*([a-zа-я0-9])*)*([a-zа-я0-9]+)$";

    @Override
    public void initialize(ValidCompanyAlias constraintAnnotation) {
    }

    @Override
    public boolean isValid(String companyAlias, ConstraintValidatorContext context) {
        return (validateCompanyAlias(companyAlias));
    }

    private boolean validateCompanyAlias(String companyAlias) {
        pattern = Pattern.compile(COMPANY_ALIAS_PATTERN);
        matcher = pattern.matcher(companyAlias);
        return matcher.matches();
    }
}
