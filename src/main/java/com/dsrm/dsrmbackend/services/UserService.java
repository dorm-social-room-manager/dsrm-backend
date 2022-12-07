package com.dsrm.dsrmbackend.services;

import com.dsrm.dsrmbackend.mappers.UserMapper;
import com.dsrm.dsrmbackend.tablesrepo.UserRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

}
