package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {
    Room addRoom(RoomRequestDTO roomDto);
    Optional<Room> getRoom(String roomId);
    Page<Room> getRooms(Pageable pageable);
    Room updateRoom(RoomRequestDTO update, String roomId);

    Optional<Room> deleteRoom(String id);
}
