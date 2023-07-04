package com.dsrm.dsrmbackend.validation.annotations;

import com.dsrm.dsrmbackend.validation.validators.ExistingRoomValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingRoomValidator.class)
@Documented
public @interface ExistingRoom {
    String message() default "Room does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}