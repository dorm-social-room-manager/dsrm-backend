package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {TestInitializer.class})
public class RoomTypeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addRoomType() throws Exception {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        roomTypeRequestDTO.setName("Ping-Pong");
        this.mockMvc.perform(post("/room-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeRequestDTO)))
                .andExpect(status().isCreated())
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
                this.mockMvc.perform(get("/room-types?page=0%size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].name").value("Pokoj mieszkalny"))
                        .andExpect(jsonPath("$.content[1].name").value("Sala telewizyjna"));
    }

}
