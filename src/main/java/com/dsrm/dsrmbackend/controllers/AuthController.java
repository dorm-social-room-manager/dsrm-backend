package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.dsrm.dsrmbackend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDetailsRequestDTO userDetails) throws CredentialException {
        if (userDetails.getUsername().equals("user")) {
            if (userDetails.getPassword().equals("user")) {
                String accessToken = authService.generateAccessToken(userDetails.getUsername());
                String refreshToken = authService.generateRefreshToken(userDetails.getUsername());
                return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
            }
            throw new CredentialException("Bad password");
        }
        else if (userDetails.getUsername().equals("admin")) {
            if (userDetails.getPassword().equals("admin")) {
                String accessToken = authService.generateAccessToken(userDetails.getUsername());
                String refreshToken = authService.generateRefreshToken(userDetails.getUsername());
                return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
            }
            throw new CredentialException("Bad password");
        }
        throw new CredentialException("User does not exist");
    }
}
