package com.dsrm.dsrmbackend.tablesrepo;

import com.dsrm.dsrmbackend.tables.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Long> {
}
