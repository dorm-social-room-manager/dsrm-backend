package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;

import javax.security.auth.login.CredentialException;

public interface AuthService {

    JwtResponse authenticateUser(LoginDetailsRequestDTO loginDetails) throws CredentialException;
}
