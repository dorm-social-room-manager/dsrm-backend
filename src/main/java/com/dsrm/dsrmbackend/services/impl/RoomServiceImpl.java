package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.repositories.RoomRepo;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepo repository;
    public Room addRoom(RoomRequestDTO roomDto) {
        Room room = RoomMapper.INSTANCE.RoomReqDtoToRoom(roomDto);
        repository.save(room);
        return room;
    }

    public Optional<Room> getRoom(Long roleId) {
        return repository.findById(roleId);
    }

    public Page<Room> getRooms(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
