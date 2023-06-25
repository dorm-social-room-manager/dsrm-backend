package com.dsrm.dsrmbackend.validation.annotations;

import com.dsrm.dsrmbackend.validation.validators.NotPastDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotPastDateTimeValidator.class)
@Documented
public @interface NotPastDateTime {
    String message() default "from should not be a date from the past";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}