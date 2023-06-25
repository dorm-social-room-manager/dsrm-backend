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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private ReservationRepo reservationRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void addValidReservation() throws Exception {
        ReservationRequestDTO reservationRequestDTO  = new ReservationRequestDTO();
        reservationRequestDTO.setRoom("1");
        reservationRequestDTO.setFrom(LocalDateTime.parse("2023-07-21T12:20:00"));
        reservationRequestDTO.setTo(LocalDateTime.parse("2023-07-22T13:20:00"));
        reservationRequestDTO.setUser("1");
        MvcResult result = this.mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString((reservationRequestDTO))))
                        .andExpect(status().isCreated())
                .andReturn();
        String reservationId = JsonPath.read(result.getResponse().getHeader("Location"), "$");
        reservationId = reservationId.substring(reservationId.length()-36);
        Optional<Reservation> resReservation = reservationRepo.findById(reservationId);
        assertTrue(resReservation.isPresent());
        Reservation reservation = resReservation.get();
        assertEquals("1", reservation.getRoom().getId());
        assertEquals("1", reservation.getUser().getId());
        assertEquals(LocalDateTime.parse("2023-07-21T12:20:00"), reservation.getStartTime());
        assertEquals(LocalDateTime.parse("2023-07-22T13:20:00"), reservation.getEndTime());
    }

    @Test
    void tryToAddInvalidReservation() throws Exception {
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
    void tryToAddReservationWithPastDate() throws Exception {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);

        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setFrom(pastDateTime);
        reservationRequestDTO.setTo(LocalDateTime.now().plusDays(1));

        this.mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.containsInAnyOrder("from should not be a date in the past",
                        "user must not be null",
                        "room must not be null")));
    }

    @Test
    void tryToAddReservationWithInvalidDateOrder() throws Exception {
        LocalDateTime fromDateTime = LocalDateTime.now().plusDays(2);
        LocalDateTime toDateTime = LocalDateTime.now().plusDays(1);

        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setFrom(fromDateTime);
        reservationRequestDTO.setTo(toDateTime);

        this.mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$",Matchers.containsInAnyOrder("this should not be before from",
                        "user must not be null",
                        "room must not be null")));

    }
    @Test
    @Transactional
    void deleteValidReservation() throws Exception{
        this.mockMvc.perform(delete("/admin/reservations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertFalse(reservationRepo.existsById("1"));

    }


    @Test
    @Transactional
    void updateValidReservation() throws Exception {
        LocalDateTime from,to;
        from = LocalDateTime.now().plusDays(1);
        to = LocalDateTime.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fromDate = from.format(formatter);
        String toDate = to.format(formatter);
        String roomID = "1";
        String userID = "2";
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setRoom(roomID);
        reservationRequestDTO.setFrom(from);
        reservationRequestDTO.setTo(to);
        reservationRequestDTO.setUser(userID);
        this.mockMvc.perform(put("/admin/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/reservations/1"));

        Optional<Reservation> reservation = reservationRepo.findById("1");
        assertTrue(reservation.isPresent());
        Reservation reservation1 = reservation.get();
        assertEquals(111, reservation1.getRoom().getRoomNumber());
        assertEquals(userID, reservation1.getUser().getId());
        assertEquals(LocalDateTime.parse(fromDate), reservation1.getStartTime());
        assertEquals(LocalDateTime.parse(toDate), reservation1.getEndTime());
    }

    @Test
    @Transactional
    void addReservationThroughUpdate() throws Exception {
        LocalDateTime from,to;
        from = LocalDateTime.now().plusDays(1);
        to = LocalDateTime.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fromDate = from.format(formatter);
        String toDate = to.format(formatter);
        String roomID = "1";
        String userID = "3";
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setRoom(roomID);
        reservationRequestDTO.setFrom(from);
        reservationRequestDTO.setTo(to);
        reservationRequestDTO.setUser(userID);
        this.mockMvc.perform(put("/admin/reservations/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", "http://localhost/admin/reservations/100"));

        Optional<Reservation> reservation = reservationRepo.findById("100");
        assertTrue(reservation.isPresent());
        Reservation reservation1 = reservation.get();
        assertEquals(111, reservation1.getRoom().getRoomNumber());
        assertEquals(userID, reservation1.getUser().getId());
        assertEquals(LocalDateTime.parse(fromDate), reservation1.getStartTime());
        assertEquals(LocalDateTime.parse(toDate), reservation1.getEndTime());
    }


    @Test
    void updateInvalidReservationWithPastDate() throws Exception {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);

        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setFrom(pastDateTime);
        reservationRequestDTO.setTo(LocalDateTime.now().plusDays(1));

        this.mockMvc.perform(put("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Matchers.containsInAnyOrder("from should not be a date in the past",
                        "user must not be null",
                        "room must not be null")));
    }

    @Test
    void updateInvalidReservationWithWrongTime() throws Exception {
        LocalDateTime fromDateTime = LocalDateTime.now().plusDays(2);
        LocalDateTime toDateTime = LocalDateTime.now().plusDays(1);

        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setFrom(fromDateTime);
        reservationRequestDTO.setTo(toDateTime);

        this.mockMvc.perform(put("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$",Matchers.containsInAnyOrder("to should not be before from",
                        "user must not be null",
                        "room must not be null")));
    }
}