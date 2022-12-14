package com.dsrm.dsrmbackend.controllers;
<<<<<<< HEAD

=======
import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dsrm.dsrmbackend.dto.RoomRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/rooms", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addRoom(@RequestBody RoomRequestDTO roomRequestDTO) {}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDTO> getRoom(@PathVariable Long id){
        return null;
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<Page<RoomDTO>> readRooms(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
>>>>>>> 1dc546d (fix: Renamed RoomControler to RoomController)
}
