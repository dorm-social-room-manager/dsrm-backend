package com.dsrm.dsrmbackend.controllers;



import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addUser(@RequestBody UserRequestDTO userRequestDTO) {}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        return null;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Page<UserDTO>> readUsers(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(),HttpStatus.OK);
    }
}
