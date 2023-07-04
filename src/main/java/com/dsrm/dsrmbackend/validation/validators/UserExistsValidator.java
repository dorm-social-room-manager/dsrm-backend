package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.validation.annotations.UserExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void initialize(UserExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext context) {
       if (userId == null) {
            return true;
       }
        return userRepo.existsById(userId);
    }
}