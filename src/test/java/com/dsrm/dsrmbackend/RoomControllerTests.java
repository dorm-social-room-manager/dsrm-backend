package com.dsrm.dsrmbackend;

import com.dsrm.dsrmbackend.repositories.RoomRepo;
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
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
public class RoomControllerTests extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoomRepo roomRepo;

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
        this.mockMvc.perform(get("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "2"))
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
