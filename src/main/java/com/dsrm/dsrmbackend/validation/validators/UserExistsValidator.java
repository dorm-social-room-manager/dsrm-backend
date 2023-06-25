package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.validation.annotations.UserExists;
import com.dsrm.dsrmbackend.validation.annotations.UserNotBanned;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

    @Autowired
    private UserRepo userRepo;

    private String defaultMessage;

    @Override
    public void initialize(UserExists constraintAnnotation) {
        defaultMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext context) {
       if (userId == null) {
            return true;
       }
        if (userRepo.findById(userId).isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(defaultMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}