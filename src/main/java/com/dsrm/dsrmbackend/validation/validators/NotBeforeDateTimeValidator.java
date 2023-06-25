package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.validation.annotations.NotBeforeDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBeforeDateTimeValidator implements ConstraintValidator<NotBeforeDateTime, ReservationRequestDTO> {
    @Override
    public void initialize(NotBeforeDateTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReservationRequestDTO reservation, ConstraintValidatorContext context) {
        if (reservation.getFrom() == null || reservation.getTo() == null)
            return true;
        return !reservation.getTo().isBefore(reservation.getFrom());
    }
}