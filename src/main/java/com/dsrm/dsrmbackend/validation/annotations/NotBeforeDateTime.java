package com.dsrm.dsrmbackend.validation.annotations;

import com.dsrm.dsrmbackend.validation.validators.NotBeforeDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBeforeDateTimeValidator.class)
@Documented
public @interface NotBeforeDateTime {
    String message() default "to should not be before from";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}