package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.entities.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<Role> toRolesMap(List<Long> id);

    Set<Role> toSetMap(List<Role> list);

    Role toRole(Long id);

}
