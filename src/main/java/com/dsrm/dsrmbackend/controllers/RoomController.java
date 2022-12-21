package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.services.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/rooms", consumes= MediaType.APPLICATION_JSON_VALUE)
    Room addRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        return roomService.addRoom(roomRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDTO> getRoom(@PathVariable Long id){
        Optional<Room> room = roomService.getRoom(id);
        return room.map(value -> new ResponseEntity<>(RoomMapper.INSTANCE.RoomToRoomDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<Page<RoomDTO>> readRooms(Pageable pageable) {
        return new ResponseEntity<>(roomService.getRooms(pageable).map(RoomMapper.INSTANCE::RoomToRoomDTO),HttpStatus.OK);
    }
}