package com.dsrm.dsrmbackend;


import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import com.dsrm.dsrmbackend.repositories.ReservationRepo;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
@DirtiesContext
class ReservationControllerTests  extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepo reservationRepo;

    @Test
    void retrieveNonExistingReservation() throws Exception {
        this.mockMvc
                .perform(get("/reservations/1000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveExistingReservation() throws Exception {
        this.mockMvc.perform(get("/reservations/1").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(jsonPath("$.room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.from", equalTo("2023-02-02 12:00:00")))
                .andExpect(jsonPath("$.to", equalTo("2023-02-02 13:00:00")))
                .andExpect(jsonPath("$.user.name", equalTo("Stefan")))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveReservationsInRange() throws Exception {
        this.mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.content[0].from", equalTo("2023-02-02 12:00:00")))
                .andExpect(jsonPath("$.content[0].to", equalTo("2023-02-02 13:00:00")))
                .andExpect(jsonPath("$.content[0].user.name", equalTo("Stefan")))
                .andExpect(jsonPath("$.content[1].room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.content[1].from", equalTo("2023-02-03 12:00:00")))
                .andExpect(jsonPath("$.content[1].to", equalTo("2023-02-03 13:00:00")))
                .andExpect(jsonPath("$.content[1].user.name", equalTo("Piotr")));
    }

    @Test
    void retrieveReservationsForSpecificUser() throws Exception {
        this.mockMvc.perform(get("/reservations?userId=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.content[0].from", equalTo("2023-02-03 12:00:00")))
                .andExpect(jsonPath("$.content[0].to", equalTo("2023-02-03 13:00:00")))
                .andExpect(jsonPath("$.content[0].user.name", equalTo("Piotr")));
    }

    @Test
    void tryToAddInvalidReservation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ReservationRequestDTO reservationRequestDTO  = new ReservationRequestDTO();
        reservationRequestDTO.setUser(null);
        reservationRequestDTO.setRoom(null);
        reservationRequestDTO.setFrom(null);
        reservationRequestDTO.setTo(null);
        this.mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect((jsonPath("$", Matchers.containsInAnyOrder("user must not be null",
                        "room must not be null",
                        "from must not be null",
                        "to must not be null"))));
    }

}