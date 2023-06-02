package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomTypeService {
    RoomType addRoomType(RoomTypeRequestDTO roomTypeReqDto);
    Optional<RoomType> getRoomType(String roomId);

    Page<RoomType> getRoomTypes(Pageable pageable);

    void deleteRoomType(String id);
}

