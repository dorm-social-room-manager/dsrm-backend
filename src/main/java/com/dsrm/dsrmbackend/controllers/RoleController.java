package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.dsrm.dsrmbackend.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
public class RoleController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addRole(@RequestBody RoleRequestDTO roleRequestDTO) {
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        return null;
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Page<RoleDTO>> readRoles(Pageable pageable) {
        return new ResponseEntity<>(Page.empty(), HttpStatus.OK);
    }
}
