package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.repositories.RoomTypeRepo;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepo roomRepo;
    private final RoomTypeRepo roomTypeRepo;
    private final RoomMapper roomMapper;
    public Room addRoom(RoomRequestDTO roomDto) {
        Room room = roomMapper.roomReqDTOToRoom(roomDto);
        room.setId(UUID.randomUUID().toString());
        return roomRepo.save(room);
    }

    public Optional<Room> getRoom(String roomId) {
        return roomRepo.findById(roomId);
    }

    public Page<Room> getRooms(Pageable pageable) {
        return roomRepo.findAll(pageable);
    }

    @Override
    public Room updateRoom(RoomRequestDTO update, String roomId) {
        Optional<Room> room = roomRepo.findById(roomId);
        Room newRoom;
        if (room.isPresent()) {
            newRoom = room.get();
            newRoom.setRoomNumber(update.getNumber());
            newRoom.setFloor(update.getFloor());
            Optional<RoomType> roomType = roomTypeRepo.findById(update.getType());
            if (newRoom.getRoomType() != null && roomType.isPresent()) {
                newRoom.setRoomType(roomType.get());
            }
            newRoom.setMaxCapacity(update.getMaxCapacity());
            newRoom.setOpeningTime(update.getOpeningTime());
            newRoom.setClosingTime(update.getClosingTime());
        }
        else {
            newRoom = roomMapper.roomReqDTOToRoom(update);
            newRoom.setId(roomId);
        }
        return roomRepo.save(newRoom);
    }
}
