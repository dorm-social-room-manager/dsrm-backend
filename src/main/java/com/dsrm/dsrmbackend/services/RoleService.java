package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    Role addRole(RoleRequestDTO roleRequestDto);

    Optional<Role> getRole(Long roleId);

    Page<Role> getRoles(Pageable pageable);
}