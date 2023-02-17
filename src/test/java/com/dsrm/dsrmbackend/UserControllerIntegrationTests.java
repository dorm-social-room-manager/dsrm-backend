package com.dsrm.dsrmbackend;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
class UserControllerIntegrationTests extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Test
    void retrieveNonExistingUser() throws Exception {
        this.mockMvc
                .perform(get("/users/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveExistingUser() throws Exception {
        this.mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.email", equalTo("test01@wp.pl")))
        .andExpect(jsonPath("$.name", equalTo("Jan")))
        .andExpect(jsonPath("$.surname", equalTo("Kowalski")))
        .andExpect(status().isOk());
    }

    @Test
    void addValidUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDTO userRequestDTO  = new UserRequestDTO();
        userRequestDTO.setName("Jan");
        userRequestDTO.setEmail("Jan@gmail.com");
        userRequestDTO.setSurname("Chraboszcz");
        userRequestDTO.setPassword("Marciniak");
        userRequestDTO.setRoles(null);
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/4"));
    }

    @Test
    void retrieveUsersInRange() throws Exception {
        this.mockMvc.perform(get("/users?page=0%size=2")
                 .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Jan"))
                .andExpect(jsonPath("$.content[0].surname").value("Kowalski"))
                .andExpect(jsonPath("$.content[0].email").value("test01@wp.pl"))
                .andExpect(jsonPath("$.content[1].name").value("Piotr"))
                .andExpect(jsonPath("$.content[1].surname").value("Nowak"))
                .andExpect(jsonPath("$.content[1].email").value("test02@wp.pl"));
    }

    @Test
    void retrieveUsersWithoutRoles() throws Exception {
        this.mockMvc.perform(get("/users?isPending=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Stefan"))
                .andExpect(jsonPath("$.content[0].surname").value("Grabowski"))
                .andExpect(jsonPath("$.content[0].email").value("test03@wp.pl"));

    }


    @Test
    void validPatchExistingUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRolesOnlyDTO userRolesOnlyDTO  = new UserRolesOnlyDTO();
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        userRolesOnlyDTO.setRoles(longs);
        this.mockMvc.perform(patch("/users/1/roles").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRolesOnlyDTO))
                ).andExpect(status().isOk());
        Optional<User> optionalUser = userRepo.findById(1L);
        Assertions.assertNotNull(optionalUser);
        Assertions.assertEquals("Administrator", optionalUser.get().getRoles().stream().toList().get(0).getName());
    }

    @Test
    void patchNonExistingUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRolesOnlyDTO userRolesOnlyDTO  = new UserRolesOnlyDTO();
        List<Long> longs = new ArrayList<>();
        longs.add(1L);
        userRolesOnlyDTO.setRoles(longs);
        this.mockMvc.perform(patch("/users/100/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRolesOnlyDTO))
                ).andExpect(forwardedUrl(null))
                 .andExpect(status().isNotFound());
    }
}
