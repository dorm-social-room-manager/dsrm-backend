package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public User addUser(UserRequestDTO userRequestDTO){
        User user = userMapper.toUser(userRequestDTO);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Page<User> getUsers(Pageable pageable){
        return userRepo.findAll(pageable);
    }

    @Override
    public Page<User> getUsersWithRoles(Role roles, Pageable pageable) {
        return userRepo.findUserByRoles(roles,pageable);
    }

}
