package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;

import javax.security.auth.login.CredentialException;
import java.security.Key;

public interface AuthService {

    Key getSigningKey();

    JwtResponse authenticateUser(LoginDetailsRequestDTO loginDetails) throws CredentialException;

    JwtResponse refreshTokens(String refreshToken) throws CredentialException;
}
