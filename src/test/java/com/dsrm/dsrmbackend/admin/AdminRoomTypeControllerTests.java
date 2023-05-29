package com.dsrm.dsrmbackend.admin;

import com.dsrm.dsrmbackend.AbstractIntegrationTest;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.repositories.RoomTypeRepo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
public class AdminRoomTypeControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoomTypeRepo roomTypeRepo;

    @Autowired
    RoomRepo roomRepo;
    @Test
    @Transactional
    public void addRoomType() throws Exception {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO();
        roomTypeRequestDTO.setName("Ping-Pong");
        MvcResult result = this.mockMvc.perform(post("/admin/room-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String roomTypeId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        roomTypeId = roomTypeId.substring(roomTypeId.length()-36);
        Optional<RoomType> resRoomType = roomTypeRepo.findById(roomTypeId);
        assertTrue(resRoomType.isPresent());
        RoomType roomType = resRoomType.get();
        assertEquals("Ping-Pong", roomType.getName());
    }
    @Test
    public void tryToAddNamelessRoomType() throws Exception {
        RoomTypeRequestDTO roomTypeRequestDTO = new RoomTypeRequestDTO();
        roomTypeRequestDTO.setName("");
        this.mockMvc.perform(post("/admin/room-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomTypeRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("name must not be blank"));
    }

    @Transactional
    @Test
    public void deleteExistentRoomType() throws Exception {
        this.mockMvc.perform(delete("/admin/room-types/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("Sala telewizyjna"));
        assertThat(roomTypeRepo.findById("2")).isEmpty();
        Optional<Room> room = roomRepo.findById("3");
        assertThat(room).isNotEmpty();
        assertNull(room.get().getRoomType());
    }

    @Test
    public void tryToDeleteNonExistentRoomType() throws Exception {
        this.mockMvc.perform(delete("/admin/room-types/2222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
