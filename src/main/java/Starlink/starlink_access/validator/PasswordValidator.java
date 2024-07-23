package Starlink.starlink_access.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation){

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if (value == null){
            return false;
        }

        return value.length() >= 8 && value.chars().anyMatch(Character::isUpperCase);
    }
}
