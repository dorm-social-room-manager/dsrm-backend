package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import com.dsrm.dsrmbackend.entities.Room;
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

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/reservations/{id}")
    public ResponseEntity<Void> updateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO, @PathVariable String id) {
        Reservation reservation = reservationService.updateReservation(reservationRequestDTO, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(reservation.getId()).toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).build();
    }

}
