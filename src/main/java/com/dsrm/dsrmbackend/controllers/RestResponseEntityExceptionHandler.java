package com.dsrm.dsrmbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            if (errorMessage != null) {
                errors.add(errorMessage);
            } else if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String fieldName = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                if (defaultMessage != null) {
                    errors.add(fieldName + " " + defaultMessage);
                } else {
                    errors.add(fieldName);
                }
            }
        });
        return errors;
    }

    @ExceptionHandler(CredentialException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleCredentialException(CredentialException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return errors;
    }

}
