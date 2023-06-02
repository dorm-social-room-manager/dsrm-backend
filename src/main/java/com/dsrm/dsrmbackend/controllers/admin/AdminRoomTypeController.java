package com.dsrm.dsrmbackend.controllers.admin;


import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRoomTypeController {
    private final RoomTypeService roomTypeService;

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

    @DeleteMapping(value="/room-types/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable String id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }
}
