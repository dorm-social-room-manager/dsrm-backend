package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.mappers.RoomMapper;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController("AdminRoomController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RoomController {
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
    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDTO> getRoom(@PathVariable String id){
        Optional<Room> room = roomService.getRoom(id);
        return ResponseEntity.of(room.map(roomMapper::roomToRoomDTO));
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<Page<RoomDTO>> readRooms(Pageable pageable) {
        return new ResponseEntity<>(roomService.getRooms(pageable).map(roomMapper::roomToRoomDTO),HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/rooms/{id}")
    public ResponseEntity<Void> updateRoom(@RequestBody RoomRequestDTO roomRequestDTO, @PathVariable String id) {
        Room room = roomService.updateRoom(roomRequestDTO, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(room.getId()).toUri();

        return ResponseEntity.status(HttpStatus.OK).location(location).build();
    }
}
