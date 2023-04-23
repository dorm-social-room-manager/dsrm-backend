package com.dsrm.dsrmbackend.controllers.admin;

import com.dsrm.dsrmbackend.dto.RoleDTO;
import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.mappers.RoleMapper;
import com.dsrm.dsrmbackend.services.RoleService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        Role role = roleService.addRole(roleRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> getRole(@PathVariable String id) {
        Optional<Role> role = roleService.getRole(id);
        return ResponseEntity.of(role.map(roleMapper::roleToRoleDTO));
    }

    @GetMapping(value = "/roles")
    @PageableAsQueryParam
    public ResponseEntity<Page<RoleDTO>> readRoles(@Schema(hidden = true) Pageable pageable) {
        return new ResponseEntity<>(roleService.getRoles(pageable).map(roleMapper::roleToRoleDTO),HttpStatus.OK);
    }
}
