package peaksoft.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
            String pattern = "^.*(?=.{8,})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$";
            if (password.length() >= 8) {
                return password.matches(pattern);
            }
            return false;
    }
}
