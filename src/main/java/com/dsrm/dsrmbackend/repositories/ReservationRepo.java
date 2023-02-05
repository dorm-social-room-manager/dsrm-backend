package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,String> {
    Page<Reservation> findAll(Specification<Reservation> reservationSpecification, Pageable pageable);
}
