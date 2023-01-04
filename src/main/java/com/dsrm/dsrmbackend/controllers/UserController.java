package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", consumes= MediaType.APPLICATION_JSON_VALUE)
    User addUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.addUser(userRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Optional<UserDTO>> getUser(@PathVariable Long id){
        Optional<User> user = userService.getUser(id);
        return ResponseEntity.of(Optional.of(user.map(userMapper::toUserDTO)));
    }

    @GetMapping(value = "/users")
    @PageableAsQueryParam
    public ResponseEntity<Page<UserDTO>> readUsers(@Parameter(hidden = true) Pageable pageable) {
        Page<User> userPage = userService.getUsers(pageable);
        return new ResponseEntity<>(userPage.map(userMapper::toUserDTO),HttpStatus.OK);
    }
}
