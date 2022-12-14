package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/reservations", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {}

}
