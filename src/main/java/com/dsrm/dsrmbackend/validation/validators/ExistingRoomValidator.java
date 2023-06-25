package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.validation.annotations.ExistingRoom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingRoomValidator implements ConstraintValidator<ExistingRoom, String> {
    @Autowired
    private RoomRepo roomRepo;

    private String defaultMessage;

    @Override
    public void initialize(ExistingRoom constraintAnnotation) {
        defaultMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String roomId, ConstraintValidatorContext context) {
        if (roomId == null){
            return true;
        }
        if (roomRepo.findById(roomId).isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(defaultMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}