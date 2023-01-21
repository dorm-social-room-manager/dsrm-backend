package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Optional<User> updateUser(UserRolesOnlyDTO userRolesOnlyDTO, Long id) {
        final Optional<User> user = userRepo.findById(id);
        user.ifPresent(userEntity -> updateUserRoles(userRolesOnlyDTO, userEntity));
        return user;
    }

    @Override
    public void updateUserRoles(UserRolesOnlyDTO userRolesOnlyDTO, User user) {
        if (userRolesOnlyDTO == null || userRolesOnlyDTO.getRoles() == null)
            return;
        User userRole = userMapper.toUserRoles(userRolesOnlyDTO);
        user.setRoles(userRole.getRoles());
        userRepo.save(user);
    }

    @Override
    public Page<User> getUsers(Pageable pageable, boolean isPending){
        Specification<User> userSpecification = Specification.where(null);
        if (isPending) {
            userSpecification = userSpecification.and(
                    (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.isEmpty(root.get("roles"))
            );
        }
        return userRepo.findAll(userSpecification, pageable);
    }

}
