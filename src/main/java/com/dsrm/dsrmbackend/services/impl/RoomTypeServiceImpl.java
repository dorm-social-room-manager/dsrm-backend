package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomTypeMapper;
import com.dsrm.dsrmbackend.repositories.RoomTypeRepo;
import com.dsrm.dsrmbackend.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepo roomTypeRepo;
    private final RoomTypeMapper roomTypeMapper;
    public RoomType addRoomType(RoomTypeRequestDTO roomTypeReqDto) {
        RoomType roomType = roomTypeMapper.roomTypeReqDTOToRoomType(roomTypeReqDto);
        roomType.setId(UUID.randomUUID().toString());
        return roomTypeRepo.save(roomType);
    }

    public Optional<RoomType> getRoomType(String roomId) {
        return roomTypeRepo.findById(roomId);
    }

    public Page<RoomType> getRoomTypes(Pageable pageable) {
        return roomTypeRepo.findAll(pageable);
    }

    @Override
    public Optional<RoomType> deleteRoomType(String id) {
        Optional<RoomType> roomType = roomTypeRepo.findById(id);
        roomTypeRepo.deleteById(id);
        return roomType;
    }
}
