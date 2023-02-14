package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${dsrm.auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${dsrm.auth.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    private Key getSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String generateAccessToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(this.getSigningKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs)).signWith(this.getSigningKey())
                .compact();
    }
}
