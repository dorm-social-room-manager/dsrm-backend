package com.dsrm.dsrmbackend.admin;

import com.dsrm.dsrmbackend.AbstractIntegrationTest;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.repositories.ReservationRepo;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
class AdminUserControllerIntegrationTests extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReservationRepo  reservationRepo;

    @Test
    void retrieveNonExistingUser() throws Exception {
        this.mockMvc
                .perform(get("/admin/users/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveExistingUser() throws Exception {
        this.mockMvc.perform(get("/admin/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$.email", equalTo("test01@wp.pl")))
        .andExpect(jsonPath("$.name", equalTo("Jan")))
        .andExpect(jsonPath("$.surname", equalTo("Kowalski")))
        .andExpect(jsonPath("$.roomNumber", equalTo(111)))
        .andExpect(jsonPath("$.id", equalTo("1")))
        .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void addValidUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDTO userRequestDTO  = new UserRequestDTO();
        userRequestDTO.setName("Jan");
        userRequestDTO.setEmail("Jan@gmail.com");
        userRequestDTO.setSurname("Chraboszcz");
        userRequestDTO.setPassword("Marciniak");
        userRequestDTO.setRoomNumber(111);
        userRequestDTO.setRoles(null);
        MvcResult result = this.mockMvc.perform(post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String userId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        userId = userId.substring(userId.length()-36);
        Optional<User> resUser = userRepo.findById(userId);
        assertTrue(resUser.isPresent());
        User user = resUser.get();
        assertEquals("Jan", user.getName());
        assertEquals("Jan@gmail.com", user.getEmail());
        assertEquals("Chraboszcz", user.getSurname());
        assertEquals("Marciniak", user.getPassword());
        assertEquals(111, user.getRoomNumber());
        assertNull(user.getRoles());
    }

    @Test
    void retrieveUsersInRange() throws Exception {
        this.mockMvc.perform(get("/admin/users")
                        .param("page", "0")
                        .param("size", "2")
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
        this.mockMvc.perform(get("/admin/users?isPending=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Stefan"))
                .andExpect(jsonPath("$.content[0].surname").value("Grabowski"))
                .andExpect(jsonPath("$.content[0].email").value("test03@wp.pl"));

    }


    @Test
    @Transactional
    void validPatchExistingUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRolesOnlyDTO userRolesOnlyDTO  = new UserRolesOnlyDTO();
        List<String> longs = new ArrayList<>();
        longs.add(String.valueOf(1L));
        userRolesOnlyDTO.setRoles(longs);
        this.mockMvc.perform(patch("/admin/users/1/roles").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRolesOnlyDTO))
                ).andExpect(status().isOk());
        Optional<User> optionalUser = userRepo.findById(String.valueOf(1L));
        assertTrue(optionalUser.isPresent());
        Assertions.assertEquals("Administrator", optionalUser.get().getRoles().stream().toList().get(0).getName());
    }

    @Test
    void patchNonExistingUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRolesOnlyDTO userRolesOnlyDTO  = new UserRolesOnlyDTO();
        List<String> longs = new ArrayList<>();
        longs.add(String.valueOf(1L));
        userRolesOnlyDTO.setRoles(longs);
        this.mockMvc.perform(patch("/admin/users/100/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRolesOnlyDTO))
                ).andExpect(forwardedUrl(null))
                 .andExpect(status().isNotFound());
    }

    @Test
    void tryToAddInvalidUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequestDTO userRequestDTO  = new UserRequestDTO();
        userRequestDTO.setName("");
        userRequestDTO.setEmail("");
        userRequestDTO.setSurname("");
        userRequestDTO.setRoomNumber(null);
        userRequestDTO.setPassword("");
        userRequestDTO.setRoles(null);
        this.mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$", Matchers.containsInAnyOrder("email must not be blank",
                        "password must not be blank",
                        "name must not be blank",
                        "surname must not be blank",
                        "roomNumber must not be null"))));
    }

    @Test
    @Transactional
    void deleteValidUser() throws Exception{
        this.mockMvc.perform(delete("/admin/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertFalse(userRepo.existsById("2"));
        assertFalse(reservationRepo.existsById("2"));
    }


}
