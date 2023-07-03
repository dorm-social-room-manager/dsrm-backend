package com.dsrm.dsrmbackend.controllers.admin;


import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/rooms", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        Room room = roomService.addRoom(roomRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/rooms/{id}")
    public ResponseEntity<Void> updateRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO, @PathVariable String id) {
        Room room = roomService.updateRoom(roomRequestDTO, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(room.getId()).toUri();

        return ResponseEntity.status(HttpStatus.OK).location(location).build();
    }

    @DeleteMapping(value="/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();

    }
}
