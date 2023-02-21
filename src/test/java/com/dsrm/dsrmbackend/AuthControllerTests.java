package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.LoginDetailsRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        this.mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("User does not exist"));
    }

    @Test
    public void authorizeWithWrongPassword() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("user");
        userdata.setPassword("anything");
        this.mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Bad password"));
    }

    @Test
    public void authorizeWithCorrectData() throws Exception {
        LoginDetailsRequestDTO userdata = new LoginDetailsRequestDTO();
        userdata.setUsername("user");
        userdata.setPassword("user");
        this.mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userdata)))
                .andExpect(status().isOk());
    }
}
