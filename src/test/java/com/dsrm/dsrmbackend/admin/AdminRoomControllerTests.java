package com.dsrm.dsrmbackend.admin;

import com.dsrm.dsrmbackend.AbstractIntegrationTest;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.repositories.ReservationRepo;
import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
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

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
public class AdminRoomControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoomRepo roomRepo;

    @Autowired
    ReservationRepo reservationRepo;

    @Test
    @Transactional
    public void insertValidRoom() throws Exception {
        LocalTime time = LocalTime.parse("12:00:00");
        RoomRequestDTO roomRequest = new RoomRequestDTO();
        roomRequest.setName("test");
        roomRequest.setNumber(203);
        roomRequest.setFloor(2);
        roomRequest.setKeyOwner("2");
        roomRequest.setType(String.valueOf(2L));
        roomRequest.setMaxCapacity(3);
        roomRequest.setOpeningTime(time);
        roomRequest.setClosingTime(time);

        MvcResult result = this.mockMvc.perform(post("/admin/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        String roomId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        roomId = roomId.substring(roomId.length()-36);
        Optional<Room> resRoom = roomRepo.findById(roomId);
        assertTrue(resRoom.isPresent());
        Room room = resRoom.get();
        assertEquals(203, room.getRoomNumber());
        assertEquals(2, room.getFloor());
        assertEquals("2", room.getRoomType().getId());
        assertEquals(3, room.getMaxCapacity());
        assertEquals(time, room.getOpeningTime());
        assertEquals(time, room.getClosingTime());
    }


    @Test
    public void tryToAddNamelessRoom() throws Exception {
        LocalTime time = LocalTime.parse("12:00:00");
        RoomRequestDTO room = new RoomRequestDTO();
        room.setName("");
        room.setNumber(203);
        room.setFloor(2);
        room.setKeyOwner("1");
        room.setType(String.valueOf(2L));
        room.setMaxCapacity(3);
        room.setOpeningTime(time);
        room.setClosingTime(time);

        this.mockMvc.perform(post("/admin/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("name must not be blank"));
    }

    @Test
    public void tryToAddInvalidRoom() throws Exception {
        RoomRequestDTO room = new RoomRequestDTO();
        room.setName("");
        room.setNumber(null);
        room.setKeyOwner(null);
        room.setFloor(null);
        room.setType(null);
        room.setMaxCapacity(null);
        room.setOpeningTime(null);
        room.setClosingTime(null);

        this.mockMvc.perform(post("/admin/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$", Matchers.containsInAnyOrder(
                        "name must not be blank",
                        "number must not be null",
                        "keyOwner must not be null",
                        "type must not be null",
                        "maxCapacity must not be null",
                        "floor must not be null",
                        "openingTime must not be null",
                        "closingTime must not be null"))));
    }

    @Test
    @Transactional
    public void updateValidRoom() throws Exception {
        RoomRequestDTO updateRoom = new RoomRequestDTO();
        updateRoom.setName("test");
        updateRoom.setNumber(202);
        updateRoom.setFloor(2);
        updateRoom.setType(String.valueOf(2L));
        updateRoom.setMaxCapacity(22);
        updateRoom.setKeyOwner("2");
        updateRoom.setOpeningTime(LocalTime.parse("12:00:00"));
        updateRoom.setClosingTime(LocalTime.parse("22:00:00"));

        this.mockMvc.perform(put("/admin/rooms/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRoom)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/rooms/2"));

        Optional<Room> resRoom = roomRepo.findById("2");
        assertTrue(resRoom.isPresent());

        Room room = resRoom.get();
        assertEquals(202, room.getRoomNumber());
        assertEquals(2, room.getFloor());
        assertEquals("2", room.getRoomType().getId());
        assertEquals("2", room.getKeyOwner().getId());
        assertEquals(22, room.getMaxCapacity());
        assertEquals(LocalTime.parse("12:00:00"), room.getOpeningTime());
        assertEquals(LocalTime.parse("22:00:00"), room.getClosingTime());
    }

    @Test
    @Transactional
    public void updateValidRoomWithInvalidData() throws Exception {
        RoomRequestDTO updateRoom = new RoomRequestDTO();
        updateRoom.setName("");
        updateRoom.setNumber(null);
        updateRoom.setFloor(null);
        updateRoom.setType(null);
        updateRoom.setKeyOwner(null);
        updateRoom.setMaxCapacity(null);
        updateRoom.setOpeningTime(null);
        updateRoom.setClosingTime(null);

        this.mockMvc.perform(put("/admin/rooms/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRoom)))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$", Matchers.containsInAnyOrder(
                        "name must not be blank",
                        "number must not be null",
                        "keyOwner must not be null",
                        "type must not be null",
                        "maxCapacity must not be null",
                        "floor must not be null",
                        "openingTime must not be null",
                        "closingTime must not be null"))));
    }

    @Test
    @Transactional
    public void addRoomThroughUpdate() throws Exception {
        RoomRequestDTO updateRoom = new RoomRequestDTO();
        updateRoom.setName("test");
        updateRoom.setNumber(202);
        updateRoom.setFloor(2);
        updateRoom.setType(String.valueOf(2L));
        updateRoom.setMaxCapacity(22);
        updateRoom.setKeyOwner("1");
        updateRoom.setOpeningTime(LocalTime.parse("12:00:00"));
        updateRoom.setClosingTime(LocalTime.parse("22:00:00"));

        this.mockMvc.perform(put("/admin/rooms/66")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRoom)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/rooms/66"));

        Optional<Room> resRoom = roomRepo.findById("66");
        assertTrue(resRoom.isPresent());

        Room room = resRoom.get();
        assertEquals(202, room.getRoomNumber());
        assertEquals(2, room.getFloor());
        assertEquals("2", room.getRoomType().getId());
        assertEquals("1", room.getKeyOwner().getId());
        assertEquals(22, room.getMaxCapacity());
        assertEquals(LocalTime.parse("12:00:00"), room.getOpeningTime());
        assertEquals(LocalTime.parse("22:00:00"), room.getClosingTime());
    }

    @Test
    @Transactional
    public void deleteExistentRoom() throws Exception {
        this.mockMvc.perform(delete("/admin/rooms/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertFalse(reservationRepo.existsById("1"));
        assertFalse(roomRepo.existsById("1"));
    }
}
