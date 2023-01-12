package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.dto.UserDTO;
import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.User;
import org.mapstruct.Mapper;



@Mapper(componentModel =  "spring", uses = RoleMapper.class)
public interface UserMapper {

    User toUser(UserRequestDTO userRequestDTO);

    UserDTO toUserDTO(User user);

}
