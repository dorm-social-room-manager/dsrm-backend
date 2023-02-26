package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs;

    @Value("${jwt.RefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    private final UserRepo userRepo;

    private Key getSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    private String generateAccessToken(User user) {
        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .setClaims(Map.of("username", user.getEmail()))
                .addClaims(Map.of("roles", user.getRoles()))
                .signWith(this.getSigningKey())
                .compact();
    }

    private String generateRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
                .setClaims(Map.of("username", user.getEmail()))
                .signWith(this.getSigningKey())
                .compact();
    }

    @Override
    public JwtResponse authenticateUser(LoginDetailsRequestDTO loginDetails) throws CredentialException {
        Optional<User> resUser = userRepo.findByEmail(loginDetails.getUsername());
        User user = resUser.orElseThrow(() -> new CredentialException("User does not exist"));
        if(!user.getPassword().equals(loginDetails.getPassword())) {
            throw new CredentialException("Bad password");
        }
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }
}
