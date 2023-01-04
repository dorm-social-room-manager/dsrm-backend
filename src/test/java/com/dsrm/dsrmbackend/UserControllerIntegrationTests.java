package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.controllers.UserController;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.services.UserService;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {UserControllerInitializer.class})
@WebAppConfiguration
class UserControllerIntegrationTests {
    @MockBean
    private UserController userController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @org.junit.jupiter.api.Test
    void checkUserControllerExistAndContextWorkProperly() {
        ServletContext servletContext = context.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);

    }

    @org.junit.jupiter.api.Test
    void retrieveNonExistingUser() throws Exception {
        this.mockMvc
                .perform(get("http://localhost:8080/users/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @org.junit.jupiter.api.Test
    void retrieveExistingUser() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.email", equalTo("test01@wp.pl")))
         .andExpect(jsonPath("$.name", equalTo("Jan")))
         .andExpect(status().isOk())
         .andDo(print());

    }

    @org.junit.jupiter.api.Test
    void addValidUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDTO userRequestDTO  = new UserRequestDTO();
        userRequestDTO.setName("Jan");
        userRequestDTO.setEmail("Jan@gmail.com");
        userRequestDTO.setSurname("Chraboszcz");
        userRequestDTO.setPassword("Marciniak");
        userRequestDTO.setRoles(null);
        this.mockMvc.perform(post("http://localhost:8080/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated());
    }

    @org.junit.jupiter.api.Test
    void retrieveUsersInRange() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/users").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}