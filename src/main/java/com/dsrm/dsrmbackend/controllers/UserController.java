package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = userService.addUser(userRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable String id){
        Optional<User> user = userService.getUser(id);
        return  ResponseEntity.of((user.map(userMapper::toUserDTO)));
    }

}
