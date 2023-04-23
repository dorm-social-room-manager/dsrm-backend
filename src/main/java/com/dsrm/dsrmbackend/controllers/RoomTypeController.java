package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.mappers.RoomTypeMapper;
import com.dsrm.dsrmbackend.services.RoomTypeService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.dsrm.dsrmbackend.dto.RoomTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;
    private final RoomTypeMapper roomTypeMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/room-types/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable String id){
        Optional<RoomType> room = roomTypeService.getRoomType(id);
        return ResponseEntity.of(room.map(roomTypeMapper::roomTypeToRoomTypeDTO));
    }

    @GetMapping(value = "/room-types")
    @PageableAsQueryParam
    public ResponseEntity<Page<RoomTypeDTO>> readRoomTypes(@Schema(hidden = true) Pageable  pageable) {
        return new ResponseEntity<>(roomTypeService.getRoomTypes(pageable).map(roomTypeMapper::roomTypeToRoomTypeDTO), HttpStatus.OK);
    }
}
