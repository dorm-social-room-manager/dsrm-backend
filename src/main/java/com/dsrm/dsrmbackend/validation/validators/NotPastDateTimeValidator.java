package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.validation.annotations.NotPastDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NotPastDateTimeValidator implements ConstraintValidator<NotPastDateTime, LocalDateTime> {
    @Override
    public void initialize(NotPastDateTime constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext context) {
        if (dateTime == null)
            return true;
        LocalDateTime currentDateTime = LocalDateTime.now();
        return !dateTime.isBefore(currentDateTime);
    }
}