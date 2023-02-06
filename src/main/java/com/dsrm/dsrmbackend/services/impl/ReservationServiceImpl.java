package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import com.dsrm.dsrmbackend.mappers.ReservationMapper;
import com.dsrm.dsrmbackend.repositories.ReservationRepo;
import com.dsrm.dsrmbackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepo reservationRepo;
    private final ReservationMapper reservationMapper;


    @Override
    public Reservation addReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationMapper.toReservation(reservationRequestDTO);
        return reservationRepo.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {return reservationRepo.findById(id);}

    @Override
    public Page<Reservation> getReservations(Pageable pageable) {return reservationRepo.findAll(pageable);}
}