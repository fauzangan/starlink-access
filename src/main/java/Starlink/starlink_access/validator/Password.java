package Starlink.starlink_access.validator;

import jakarta.validation.Payload;

public @interface Password {
    String message() default "Password must contain at least one uppercase letter and be at least 8 characters long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
