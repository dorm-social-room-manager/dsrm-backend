package com.dsrm.dsrmbackend.services.impl;

import com.dsrm.dsrmbackend.dto.UserRequestDTO;
import com.dsrm.dsrmbackend.dto.UserRolesOnlyDTO;
import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.repositories.RoleRepo;
import com.dsrm.dsrmbackend.repositories.UserRepo;
import com.dsrm.dsrmbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;

    @Override
    public User addUser(UserRequestDTO userRequestDTO){
        User user = userMapper.toUser(userRequestDTO);
        user.setId(UUID.randomUUID().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUser(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Page<User> getUsers(Pageable pageable){
        return userRepo.findAll(pageable);
    }

    @Transactional
    @Override
    public Optional<User> updateUser(UserRolesOnlyDTO userRolesOnlyDTO, String id) {
        final Optional<User> user = userRepo.findById(id);
        user.ifPresent(userEntity -> updateUserRoles(userRolesOnlyDTO, userEntity));
        return user;
    }

    private void updateUserRoles(UserRolesOnlyDTO userRolesOnlyDTO, User user) {
        if (userRolesOnlyDTO == null || userRolesOnlyDTO.getRoles() == null)
            return;
        Set<Role> userRoles = userRolesOnlyDTO.getRoles().stream().map(roleRepo::getReferenceById).collect(Collectors.toSet());
        user.setRoles(userRoles);
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

    @Override
    public Optional<User> deleteUser(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty())
            return user;
        userRepo.deleteById(id);
        return user;
    }

}
