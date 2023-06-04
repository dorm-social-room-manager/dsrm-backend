package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
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

    @Value("${dsrm.auth.jwt.ExpirationMs}")
    private int jwtExpirationMs;

    @Value("${dsrm.auth.jwt.RefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    @Value("${dsrm.auth.jwt.SecretKey}")
    private String secretKey;

    private final UserRepo userRepo;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateAccessToken(User user) {
        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .setClaims(Map.of("username", user.getEmail()))
                .addClaims(Map.of("roles", user.getRoles().stream().map(Role::getName).toList()))
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(this.getSigningKey())
                .compact();
    }

    private String generateRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
                .setClaims(Map.of("username", user.getEmail()))
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
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

    @Override
    public JwtResponse refreshTokens(String jwtToken) throws CredentialException {
        Claims claims;
        try {
            claims = (Claims) Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parse(jwtToken)
                    .getBody();
        } catch (Exception e) {
            throw new CredentialException("Invalid token");
        }
        String username = claims.get("username", String.class);
        Optional<User> resUser = userRepo.findByEmail(username);
        User user = resUser.orElseThrow(() -> new CredentialException("User does not exist"));
        if (claims.getExpiration() == null || claims.getExpiration().before(new Date())) {
            throw new CredentialException("Token expired");
        }
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }
}
