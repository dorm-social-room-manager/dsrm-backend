package com.dsrm.dsrmbackend.controllers;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.mappers.RoleMapper;
import com.dsrm.dsrmbackend.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.dsrm.dsrmbackend.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        Role role = roleService.addRole(roleRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        Optional<Role> role = roleService.getRole(id);
        return ResponseEntity.of(role.map(roleMapper::roleToRoleDTO));
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Page<RoleDTO>> readRoles(Pageable pageable) {
        return new ResponseEntity<>(roleService.getRoles(pageable).map(roleMapper::roleToRoleDTO),HttpStatus.OK);
    }
}
