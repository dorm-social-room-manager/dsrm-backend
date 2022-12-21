package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomTypeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.services.impl.RoomTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeServiceImpl roomTypeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/room-types", consumes= MediaType.APPLICATION_JSON_VALUE)
    RoomType addRoomType(@RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {
        return roomTypeService.addRoomType(roomTypeRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/room_types/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable Long id){
        Optional<RoomType> room = roomTypeService.getRoomType(id);
        return room.map(value -> new ResponseEntity<>(RoomTypeMapper.INSTANCE.RoomTypeToRoomTypeDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/room_types")
    public ResponseEntity<Page<RoomTypeDTO>> readRoomTypes(Pageable pageable) {
        return new ResponseEntity<>(roomTypeService.getRoomTypes(pageable).map(RoomTypeMapper.INSTANCE::RoomTypeToRoomTypeDTO),HttpStatus.OK);
    }
}
