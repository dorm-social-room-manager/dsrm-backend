package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoomTypeRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomTypeController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/room-types", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addRoomType(@RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {}

}
