package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
    @Mapping(source = "number", target = "roomNumber")
    Room RoomReqDtoToRoom(RoomRequestDTO roomDto);
    RoomDTO RoomToRoomDTO(Room room);

}
