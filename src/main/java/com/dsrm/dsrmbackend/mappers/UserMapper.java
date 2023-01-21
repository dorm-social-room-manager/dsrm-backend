package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.User;
import org.mapstruct.Mapper;



@Mapper(componentModel =  "spring", uses = RoleMapper.class)
public interface UserMapper {

    User toUser(UserRequestDTO userRequestDTO);

    User toUserRoles(UserRolesOnlyDTO userRolesOnlyDTO);

    UserDTO toUserDTO(User user);

    User toUser(Long id);
}
