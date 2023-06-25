package com.dsrm.dsrmbackend.validation.annotations;

import com.dsrm.dsrmbackend.validation.validators.UserExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistsValidator.class)
@Documented
public @interface UserExists {
    String message() default "User does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}