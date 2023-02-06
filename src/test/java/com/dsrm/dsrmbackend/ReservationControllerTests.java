package com.dsrm.dsrmbackend;


import com.dsrm.dsrmbackend.mappers.ReservationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
class ReservationControllerTests  extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
                .andExpect(jsonPath("$.startTime", equalTo("2023-02-02 12:00:00")))
                .andExpect(jsonPath("$.endTime", equalTo("2023-02-02 13:00:00")))
                .andExpect(jsonPath("$.user.name", equalTo("Stefan")))
                .andExpect(status().isOk());
    }

    @Test
    void addValidReservation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("room",1);
        body.put("openingTime","2023-02-21 12:20:00");
        body.put("closingTime","2023-02-22 13:20:00");
        body.put("user",2);
        this.mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((body))))
                        .andExpect(status().isCreated())
                        .andExpect(header().string("Location", "http://localhost/reservations/3"));
    }

    @Test
    void retrieveReservationsInRange() throws Exception {
        this.mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.content[0].startTime", equalTo("2023-02-02 12:00:00")))
                .andExpect(jsonPath("$.content[0].endTime", equalTo("2023-02-02 13:00:00")))
                .andExpect(jsonPath("$.content[0].user.name", equalTo("Stefan")))
                .andExpect(jsonPath("$.content[1].room.roomNumber", equalTo(111)))
                .andExpect(jsonPath("$.content[1].startTime", equalTo("2023-02-03 12:00:00")))
                .andExpect(jsonPath("$.content[1].endTime", equalTo("2023-02-03 13:00:00")))
                .andExpect(jsonPath("$.content[1].user.name", equalTo("Piotr")));
    }
}
