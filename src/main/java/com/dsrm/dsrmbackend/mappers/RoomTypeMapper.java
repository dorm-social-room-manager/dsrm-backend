package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    @Mapping(target = "id", ignore = true)
    RoomType roomTypeReqDTOToRoomType(RoomTypeRequestDTO roomTypeReqDto);

    RoomTypeDTO roomTypeToRoomTypeDTO(RoomType roomType);

    RoomType mapIdToRoomType(String id);
}
