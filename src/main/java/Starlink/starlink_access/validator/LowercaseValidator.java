package Starlink.starlink_access.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {
    @Override
    public void initialize(Lowercase constraintAnnotation){

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        return value != null && value.equals(value.toLowerCase());
    }
}
