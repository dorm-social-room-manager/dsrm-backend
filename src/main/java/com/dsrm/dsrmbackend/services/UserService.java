package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.pageable.PaginatedUserResponse;
import com.dsrm.dsrmbackend.tables.User;
import com.dsrm.dsrmbackend.tablesrepo.UserRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    public PaginatedUserResponse readUsers(Pageable pageable) {
        Page<User> userPage = userRepo.findAll(pageable);
        return PaginatedUserResponse.builder()
                .numberOfItems(userPage.getTotalElements()).numberOfPages(userPage.getTotalPages())
                .userList(userPage.getContent())
                .build();
    }
}
