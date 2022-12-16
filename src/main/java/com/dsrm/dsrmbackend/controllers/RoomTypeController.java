package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import com.dsrm.dsrmbackend.services.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/room-types", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addRoomType(@RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/room_types/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable Long id){
        return null;
    }

    @GetMapping(value = "/room_types")
    public ResponseEntity<Page<RoomTypeDTO>> readRoomTypes(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
}
