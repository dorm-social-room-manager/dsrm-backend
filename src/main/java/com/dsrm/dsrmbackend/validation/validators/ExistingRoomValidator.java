package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.validation.annotations.ExistingRoom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingRoomValidator implements ConstraintValidator<ExistingRoom, String> {
    @Autowired
    private RoomRepo roomRepo;

    @Override
    public void initialize(ExistingRoom constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roomId, ConstraintValidatorContext context) {
        if (roomId == null){
            return true;
        }
        return roomRepo.existsById(roomId);
    }
}