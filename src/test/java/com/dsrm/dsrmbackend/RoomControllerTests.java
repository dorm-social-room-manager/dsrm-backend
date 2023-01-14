package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {TestInitializer.class})
@AutoConfigureMockMvc
public class RoomControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertValidRoom() throws Exception {
        RoomRequestDTO room = new RoomRequestDTO();
        room.setName("test");
        room.setNumber(203);
        room.setFloor(2);
        room.setType(6L);
        room.setMaxCapacity(3);
        room.setOpeningTime(LocalTime.now());
        room.setClosingTime(LocalTime.now());
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(room.getName()));
    }

    @Test
    public void getExistentRoom() throws Exception {
        this.mockMvc.perform(get("/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNumber").value(111))
                .andExpect(jsonPath("$.floor").value(1))
                .andExpect(jsonPath("$.roomType.id").value(1))
                .andExpect(jsonPath("$.maxCapacity").value(2))
                .andExpect(jsonPath("$.openingTime").value("12:00:00"))
                .andExpect(jsonPath("$.closingTime").value("23:00:00"))
                .andExpect(jsonPath("$.unavailableStart").value(is(nullValue())))
                .andExpect(jsonPath("$.unavailableEnd").value(is(nullValue())));

    }

    @Test
    public void tryToGetNonexistentRoom() throws Exception {
        this.mockMvc.perform(get("/rooms/1512")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoomsInRange() throws Exception {
        this.mockMvc.perform(get("/rooms?page=0%size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].roomNumber").value(111))
                .andExpect(jsonPath("$.content[0].floor").value(1))
                .andExpect(jsonPath("$.content[0].roomType.id").value(1))
                .andExpect(jsonPath("$.content[0].maxCapacity").value(2))
                .andExpect(jsonPath("$.content[0].openingTime").value("12:00:00"))
                .andExpect(jsonPath("$.content[0].closingTime").value("23:00:00"))
                .andExpect(jsonPath("$.content[0].unavailableStart").value(is(nullValue())))
                .andExpect(jsonPath("$.content[0].unavailableEnd").value(is(nullValue())))
                .andExpect(jsonPath("$.content[1].roomNumber").value(112))
                .andExpect(jsonPath("$.content[1].floor").value(1))
                .andExpect(jsonPath("$.content[1].roomType.id").value(1))
                .andExpect(jsonPath("$.content[1].maxCapacity").value(2))
                .andExpect(jsonPath("$.content[1].openingTime").value("12:00:00"))
                .andExpect(jsonPath("$.content[1].closingTime").value("23:00:00"))
                .andExpect(jsonPath("$.content[1].unavailableStart").value(is(nullValue())))
                .andExpect(jsonPath("$.content[1].unavailableEnd").value(is(nullValue())));

    }
}
