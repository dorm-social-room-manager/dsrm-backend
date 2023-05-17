package com.dsrm.dsrmbackend.admin;


import com.dsrm.dsrmbackend.AbstractIntegrationTest;
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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.DatabaseInitializer.class})
@AutoConfigureMockMvc
@DirtiesContext
class AdminReservationControllerTests extends  AbstractIntegrationTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ReservationRepo reservationRepo;


    @Test
    @Transactional
    void addValidReservation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("room",1);
        body.put("from","2023-02-21 12:20:00");
        body.put("to","2023-02-22 13:20:00");
        body.put("user",2);
        MvcResult result = this.mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((body))))
                        .andExpect(status().isCreated())
                .andReturn();
        String reservationId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        reservationId = reservationId.substring(reservationId.length()-36);
        Optional<Reservation> resReservation = reservationRepo.findById(reservationId);
        assertTrue(resReservation.isPresent());
        Reservation reservation = resReservation.get();
        assertEquals("1", reservation.getRoom().getId());
        assertEquals("2", reservation.getUser().getId());
        assertEquals(LocalDateTime.parse("2023-02-21T12:20:00"), reservation.getStartTime());
        assertEquals(LocalDateTime.parse("2023-02-22T13:20:00"), reservation.getEndTime());
    }

    @Test
    void tryToAddInvalidReservation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ReservationRequestDTO reservationRequestDTO  = new ReservationRequestDTO();
        reservationRequestDTO.setUser(null);
        reservationRequestDTO.setRoom(null);
        reservationRequestDTO.setFrom(null);
        reservationRequestDTO.setTo(null);
        this.mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect((jsonPath("$", Matchers.containsInAnyOrder("user must not be null",
                        "room must not be null",
                        "from must not be null",
                        "to must not be null"))));
    }


    @Test
    @Transactional
    void updateValidReservation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("room",1);
        body.put("from","2023-05-21 12:20:00");
        body.put("to","2023-05-22 13:20:00");
        body.put("user",3);

        this.mockMvc.perform(put("/admin/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/reservations/1"));

        Optional<Reservation> reservation = reservationRepo.findById("1");
        assertTrue(reservation.isPresent());
        Reservation reservation1 = reservation.get();
        assertEquals(101, reservation1.getRoom().getRoomNumber());
        assertEquals("3", reservation1.getUser().getId());
        assertEquals(LocalDateTime.parse("2023-05-21 12:20:00"), reservation1.getStartTime());
        assertEquals(LocalDateTime.parse("2023-05-22 13:20:00"), reservation1.getEndTime());
    }

    @Test
    @Transactional
    void addReservationThroughUpdate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("room",1);
        body.put("from","2023-05-21 12:20:00");
        body.put("to","2023-05-22 13:20:00");
        body.put("user",3);

        this.mockMvc.perform(put("/admin/reservations/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/reservations/100"));

        Optional<Reservation> reservation = reservationRepo.findById("100");
        assertTrue(reservation.isPresent());

        Reservation reservation1 = reservation.get();
        assertEquals(111, reservation1.getRoom().getRoomNumber());
        assertEquals("3", reservation1.getUser().getId());
        assertEquals(LocalDateTime.parse("2023-05-21T12:20:00"), reservation1.getStartTime());
        assertEquals(LocalDateTime.parse("2023-05-22T13:20:00"), reservation1.getEndTime());
    }

}