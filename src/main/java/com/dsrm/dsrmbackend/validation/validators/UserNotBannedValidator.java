package com.dsrm.dsrmbackend.validation.validators;

import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.validation.annotations.UserNotBanned;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserNotBannedValidator implements ConstraintValidator<UserNotBanned, String> {

    @Autowired
    private UserRepo userRepo;


    @Override
    public void initialize(UserNotBanned constraintAnnotation) {

    }

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext context) {
        if (userId == null) {
            return true;
        }
        Optional<User> user = userRepo.findById(userId);

        return user.map(User::getBanEnd).map(date -> date.isAfter(LocalDateTime.now())).orElse(true);
    }
}