package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    public RoomTypeMapper INSTANCE = Mappers.getMapper(RoomTypeMapper.class);
    @Mapping(target = "id", ignore = true)
    RoomType RoomTypeReqDTOToRoomType(RoomTypeRequestDTO roomTypeReqDto);

    RoomTypeDTO RoomTypeToRoomTypeDTO(RoomType roomType);
}
