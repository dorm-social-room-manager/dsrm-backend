package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.validation.annotations.UserNotBanned;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserNotBannedValidator implements ConstraintValidator<UserNotBanned, String> {

    @Autowired
    private UserRepo userRepo;

    private String defaultMessage;

    @Override
    public void initialize(UserNotBanned constraintAnnotation) {
        defaultMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext context) {
        if (userId == null) {
            return true;
        }
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent() && user.get().getBanEnd() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(defaultMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;

    }
}