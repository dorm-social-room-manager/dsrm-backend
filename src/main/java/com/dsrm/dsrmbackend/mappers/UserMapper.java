package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel =  "spring")
public interface UserMapper {

    User toUser(UserRequestDTO userRequestDTO);

    List<Role> toListMap(List<Long> id);

    Set<Role> toSetMap(List<Role> list);

    Role toRoleMap(Long id);

    UserDTO toUserDTO(User user);

}
