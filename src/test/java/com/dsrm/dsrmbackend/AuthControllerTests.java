package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
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
}
