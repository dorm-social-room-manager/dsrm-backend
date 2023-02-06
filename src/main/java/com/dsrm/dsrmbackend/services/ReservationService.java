package com.dsrm.dsrmbackend.services;


import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReservationService {

    Reservation addReservation(ReservationRequestDTO reservationRequestDTO);

    Optional<Reservation> getReservation(Long id);

    Page<Reservation> getReservations (Pageable pageable);
}
