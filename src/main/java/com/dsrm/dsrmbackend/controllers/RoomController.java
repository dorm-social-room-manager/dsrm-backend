package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.services.RoomService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> getRoom(@PathVariable String id){
        Optional<Room> room = roomService.getRoom(id);
        return ResponseEntity.of(room.map(roomMapper::roomToRoomDTO));
    }

    @GetMapping(value = "/rooms")
    @PageableAsQueryParam
    public ResponseEntity<Page<RoomDTO>> readRooms(@Schema(hidden = true) Pageable pageable) {
        return new ResponseEntity<>(roomService.getRooms(pageable).map(roomMapper::roomToRoomDTO),HttpStatus.OK);

    }

}
