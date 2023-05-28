package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import com.dsrm.dsrmbackend.mappers.ReservationMapper;
import com.dsrm.dsrmbackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/reservations", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addReservation(@Valid  @RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationService.addReservation(reservationRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(value = "reservations/{id}")
    ResponseEntity<Void> deleteReservation(@PathVariable String id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
