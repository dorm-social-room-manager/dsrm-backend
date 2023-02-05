package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
public class RoomTypeControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    public void addRoomType() throws Exception {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO();
        roomTypeRequestDTO.setName("Ping-Pong");
        MvcResult result = this.mockMvc.perform(post("/room-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String location = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        this.mockMvc.perform(get(location)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ping-Pong"));
    }

    @Test
    public void getExistentRoomType() throws Exception {
        this.mockMvc.perform(get("/room-types/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pokoj mieszkalny"));
    }

    @Test
    public void tryToGetNonexistentRoomType() throws Exception {
        this.mockMvc.perform(get("/room-types/1512")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void getRoomTypesInRange() throws Exception {
                this.mockMvc.perform(get("/room-types")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].name").value("Pokoj mieszkalny"))
                        .andExpect(jsonPath("$.content[1].name").value("Sala telewizyjna"));
    }
    @Test
    public void tryToAddNamelessRoomType() throws Exception {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO();
        roomTypeRequestDTO.setName("");
        this.mockMvc.perform(post("/room-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomTypeRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("name must not be blank"));
    }
}
