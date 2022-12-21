package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.mapstruct.factory.Mappers;

public interface RoomTypeMapper {
    RoomTypeMapper INSTANCE = Mappers.getMapper(RoomTypeMapper.class);
    RoomType RoomTypeReqDTOToRoomType(RoomTypeRequestDTO roomTypeReqDto);
    RoomTypeDTO RoomTypeToRoomTypeDTO(RoomType roomType);
}
