package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.repositories.RoomTypeRepo;
import com.dsrm.dsrmbackend.repositories.UserRepo;
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
    private final UserRepo userRepo;
    public Room addRoom(RoomRequestDTO roomDto) {
        Room room = roomMapper.roomReqDTOToRoom(roomDto);
        room.setId(UUID.randomUUID().toString());
        room.setRoomType(null);
        if (roomDto.getType() != null) {
            RoomType roomType = roomTypeRepo.getReferenceById(roomDto.getType());
            room.setRoomType(roomType);
        }
        if (roomDto.getKeyOwner() != null) {
            User user = userRepo.getReferenceById(roomDto.getKeyOwner());
            room.setKeyOwner(user);
        }
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
        Room newRoom = roomMapper.roomReqDTOToRoom(update);
        newRoom.setRoomType(null);
        if (update.getType() != null) {
            RoomType roomType = roomTypeRepo.getReferenceById(update.getType());
            newRoom.setRoomType(roomType);
        }
        newRoom.setId(roomId);

        return roomRepo.save(newRoom);
    }

    @Override
    public void deleteRoom(String id) {
        roomRepo.deleteById(id);
    }
}
