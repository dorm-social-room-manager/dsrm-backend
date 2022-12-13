package com.dsrm.dsrmbackend.controllers;

<<<<<<< HEAD
import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
=======
import com.dsrm.dsrmbackend.dto.ReservationDTO;
import com.dsrm.dsrmbackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> dcc4214 (feat: Create rest reservation endpoints to retrieve all reservations and specific reservation)
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/reservations", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/reservations/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id){
        return null;
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<Page<ReservationDTO>> readReservations(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
>>>>>>> dcc4214 (feat: Create rest reservation endpoints to retrieve all reservations and specific reservation)
}
