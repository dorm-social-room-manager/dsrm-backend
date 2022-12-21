package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomTypeMapper;
import com.dsrm.dsrmbackend.repositories.RoomTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepo repository;
    public RoomType addRoomType(RoomTypeRequestDTO roomTypeReqDto) {
        RoomType roomType = RoomTypeMapper.INSTANCE.RoomTypeReqDTOToRoomType(roomTypeReqDto);
        repository.save(roomType);
        return roomType;
    }

    public Optional<RoomType> getRoomType(Long roomId) {
        return repository.findById(roomId);
    }

    public Page<RoomType> getRoomTypes(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
