package com.dsrm.dsrmbackend.controllers;
import com.dsrm.dsrmbackend.dto.RoomDTO;
import com.dsrm.dsrmbackend.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomControler {
    private final RoomService roomService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDTO> getRoom(@PathVariable Long id){
        return null;
    }

    @GetMapping(value = "/rooms")
    public ResponseEntity<Page<RoomDTO>> readRooms(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
}
