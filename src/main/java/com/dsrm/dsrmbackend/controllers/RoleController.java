package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/roles", consumes= MediaType.APPLICATION_JSON_VALUE)
    void addRole(@RequestBody RoleRequestDTO roleRequestDTO) {}

}
