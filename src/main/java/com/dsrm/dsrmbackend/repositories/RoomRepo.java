package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Long> {
}
