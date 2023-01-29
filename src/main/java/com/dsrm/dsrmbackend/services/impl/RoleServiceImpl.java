package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.mappers.RoleMapper;
import com.dsrm.dsrmbackend.repositories.RoleRepo;
import com.dsrm.dsrmbackend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo repository;
    private final RoleMapper roleMapper;
    public Role addRole(RoleRequestDTO roleRequestDto) {
        Role role = roleMapper.roleReqDTOToRole(roleRequestDto);
        return repository.save(role);
    }

    public Optional<Role> getRole(Long roleId) {
        return repository.findById(roleId);
    }

    public Page<Role> getRoles(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
