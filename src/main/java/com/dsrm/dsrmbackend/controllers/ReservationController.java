package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import com.dsrm.dsrmbackend.mappers.ReservationMapper;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.dsrm.dsrmbackend.dto.ReservationDTO;
import com.dsrm.dsrmbackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReservationController {

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
    @GetMapping(value = "/reservations/{id}")
    ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id){
        Optional<Reservation> reservation = reservationService.getReservation(id);
        return  ResponseEntity.of((reservation.map(reservationMapper::toReservationDTO)));
    }

    @GetMapping(value = "/reservations")
    @PageableAsQueryParam
    public ResponseEntity<Page<ReservationDTO>> readReservations(@Parameter(hidden = true) Pageable pageable,@RequestParam(required = false) Long userId) {
        Page<Reservation> reservations = reservationService.getReservations(pageable,userId);
        return new ResponseEntity<>(reservations.map(reservationMapper::toReservationDTO),HttpStatus.OK);
    }
}
