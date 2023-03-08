package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.repositories.RoleRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
public class AdminRoleControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoleRepo roleRepo;

    @Test
    public void getExistentRole() throws Exception {
        this.mockMvc.perform(get("/admin/roles/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Uzytkownik"));
    }

    @Test
    public void tryToGetNonexistentRole() throws Exception {
        this.mockMvc.perform(get("/admin/roles/1487")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRolesInRange() throws Exception {
        this.mockMvc.perform(get("/admin/roles?page=0%size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Administrator"))
                .andExpect(jsonPath("$.content[1].name").value("Uzytkownik"));
    }

    @Test
    @Transactional
    public void addRole() throws Exception {
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
        roleRequestDTO.setName("Moderator");
        MvcResult result = this.mockMvc.perform(post("/admin/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String roleId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        roleId = roleId.substring(roleId.length()-36);
        Optional<Role> resRole = roleRepo.findById(roleId);
        assertTrue(resRole.isPresent());
        Role role = resRole.get();
        assertEquals("Moderator", role.getName());
    }

}
