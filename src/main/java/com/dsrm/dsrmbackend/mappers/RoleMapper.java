package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.RoleDTO;
import com.dsrm.dsrmbackend.dto.RoleRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role roleReqDTOToRole(RoleRequestDTO roleReqDto);

    RoleDTO roleToRoleDTO(Role role);

    List<Role> toRolesMap(List<Long> id);

    Set<Role> toSetMap(List<Role> list);

    Role toRole(String id);
}