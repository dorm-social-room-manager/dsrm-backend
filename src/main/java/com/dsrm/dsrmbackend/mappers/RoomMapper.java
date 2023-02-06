package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "number", target = "roomNumber")
    @Mapping(source = "type", target = "roomType")
    Room roomReqDTOToRoom(RoomRequestDTO roomDto);
    RoomType map(Long id);
    RoomDTO roomToRoomDTO(Room room);
    Room toRoom(Long id);
}
