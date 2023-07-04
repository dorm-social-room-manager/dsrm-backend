package com.dsrm.dsrmbackend.validation.annotations;

import com.dsrm.dsrmbackend.validation.validators.UserNotBannedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotBannedValidator.class)
@Documented
public @interface UserNotBanned {
    String message() default "is banned";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}