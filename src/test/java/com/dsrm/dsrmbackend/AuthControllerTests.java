package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.JwtResponse;
import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.dsrm.dsrmbackend.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
@DirtiesContext
public class AuthControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    @Test
    public void authorizeWrongUser() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("wrongUser");
        userdata.setPassword("anything");
        this.mockMvc.perform(put("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("User does not exist"));
    }

    @Test
    public void authorizeWithWrongPassword() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test02@wp.pl");
        userdata.setPassword("notAGoodPassword");
        this.mockMvc.perform(put("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Bad password"));
    }

    @Test
    public void authorizeWithCorrectData() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test01@wp.pl");
        userdata.setPassword("zaq1@WSX");

        MvcResult result = this.mockMvc.perform(put("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isOk())
                .andReturn();
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] accessToken = JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken").toString().split("\\.");
        String[] refreshToken = JsonPath.read(result.getResponse().getContentAsString(), "$.refreshToken").toString().split("\\.");

        String tokenPayload = new String(decoder.decode(accessToken[1]));
        String username = JsonPath.read(tokenPayload, "$.username");
        assertEquals("test01@wp.pl", username);
        tokenPayload = new String(decoder.decode(refreshToken[1]));
        username = JsonPath.read(tokenPayload, "$.username");
        assertEquals("test01@wp.pl", username);
    }

    @Test
    public void refreshValidToken() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test01@wp.pl");
        userdata.setPassword("zaq1@WSX");

        JwtResponse tokens = authService.authenticateUser(userdata);

        String refreshToken = tokens.getRefreshToken();
        MvcResult refreshResult = this.mockMvc.perform(put("/refresh")
                        .header("Authorization", refreshToken))
                .andExpect(status().isOk())
                .andReturn();

        String accessToken = JsonPath.read(refreshResult.getResponse().getContentAsString(), "$.accessToken").toString();
        refreshToken = JsonPath.read(refreshResult.getResponse().getContentAsString(), "$.refreshToken").toString();

        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(authService.getSigningKey())
                .build()
                .parse(accessToken)
                .getBody();

        assertEquals(claims.get("username"), userdata.getUsername());

        claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(authService.getSigningKey())
                .build()
                .parse(refreshToken)
                .getBody();

        assertEquals(claims.get("username"), userdata.getUsername());
    }

    @Test
    public void refreshTokenWithInvalidUsername() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test01@wp.pl");
        userdata.setPassword("zaq1@WSX");

        JwtResponse tokens = authService.authenticateUser(userdata);
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(authService.getSigningKey())
                .build()
                .parse(tokens.getRefreshToken())
                .getBody();

        claims.remove("username");

        String invalidRefreshToken = Jwts.builder()
                .setClaims(claims)
                .signWith(authService.getSigningKey())
                .compact();

        this.mockMvc.perform(put("/refresh")
                        .header("Authorization", invalidRefreshToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("$")).value("User does not exist"));
    }

    @Test
    public void refreshTokenWithExpiredToken() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test01@wp.pl");
        userdata.setPassword("zaq1@WSX");

        JwtResponse tokens = authService.authenticateUser(userdata);
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(authService.getSigningKey())
                .build()
                .parse(tokens.getRefreshToken())
                .getBody();

        claims.setExpiration(new Date(System.currentTimeMillis() - 3600));

        String invalidRefreshToken = Jwts.builder()
                .setClaims(claims)
                .signWith(authService.getSigningKey())
                .compact();

        this.mockMvc.perform(put("/refresh")
                        .header("Authorization", invalidRefreshToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("$")).value("Token expired"));
    }

    @Test
    public void refreshTokenWithoutExpiration() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("test01@wp.pl");
        userdata.setPassword("zaq1@WSX");

        JwtResponse tokens = authService.authenticateUser(userdata);
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(authService.getSigningKey())
                .build()
                .parse(tokens.getRefreshToken())
                .getBody();

        claims.setExpiration(null);

        String invalidRefreshToken = Jwts.builder()
                .setClaims(claims)
                .signWith(authService.getSigningKey())
                .compact();

        this.mockMvc.perform(put("/refresh")
                        .header("Authorization", invalidRefreshToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("$")).value("No expiration date"));
    }

    @Test
    public void refreshEmptyToken() throws Exception {
        this.mockMvc.perform(put("/refresh"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(("$")).value("Missing header: Authorization"));
    }
}
