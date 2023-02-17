package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    User addUser(UserRequestDTO userRequestDTO);

    Optional<User> getUser(Long userId);

    Page<User> getUsers(Pageable pageable);

    Page<User> getUsers(Pageable pageable, boolean isPending);
}
