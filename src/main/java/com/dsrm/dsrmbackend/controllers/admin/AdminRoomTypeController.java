package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomTypeMapper;
import com.dsrm.dsrmbackend.services.RoomTypeService;
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

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRoomTypeController {
    private final RoomTypeService roomTypeService;
    private final RoomTypeMapper roomTypeMapper;

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/room-types", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addRoomType(@Valid @RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {
        RoomType roomType = roomTypeService.addRoomType(roomTypeRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roomType.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/room-types/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable String id){
        Optional<RoomType> room = roomTypeService.getRoomType(id);
        return ResponseEntity.of(room.map(roomTypeMapper::roomTypeToRoomTypeDTO));
    }

    @GetMapping(value = "/room-types")
    public ResponseEntity<Page<RoomTypeDTO>> readRoomTypes(Pageable pageable) {
        return new ResponseEntity<>(roomTypeService.getRoomTypes(pageable).map(roomTypeMapper::roomTypeToRoomTypeDTO), HttpStatus.OK);
    }
}