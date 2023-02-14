package com.dsrm.dsrmbackend.services;

public interface AuthService {
    String generateAccessToken(String username);
    String generateRefreshToken(String username);
}
