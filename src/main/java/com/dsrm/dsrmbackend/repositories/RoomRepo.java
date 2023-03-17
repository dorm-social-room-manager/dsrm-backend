package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,String> {
    List<Room> getRoomsByRoomType(RoomType roomType);
}
