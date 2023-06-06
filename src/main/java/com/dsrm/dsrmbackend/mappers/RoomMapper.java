package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "keyOwner", ignore = true)
    @Mapping(source = "number", target = "roomNumber")
    Room roomReqDTOToRoom(RoomRequestDTO roomDto);
    RoomType map(String id);
    RoomDTO roomToRoomDTO(Room room);
    Room toRoom(String id);
}
