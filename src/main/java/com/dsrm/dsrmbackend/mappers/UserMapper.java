package com.dsrm.dsrmbackend.mappers;

import com.dsrm.dsrmbackend.DTOobjects.UserDTO;
import com.dsrm.dsrmbackend.tables.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toUser(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        //user.setRoles(userDTO.getRoles());
        return user;

    }
}
