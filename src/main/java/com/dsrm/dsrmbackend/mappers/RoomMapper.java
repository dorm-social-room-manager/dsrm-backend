package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(source = "number", target = "roomNumber")
    Room roomReqDTOToRoom(RoomRequestDTO roomDto);
    RoomDTO roomToRoomDTO(Room room);

}
