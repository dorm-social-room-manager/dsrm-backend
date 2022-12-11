package com.dsrm.dsrmbackend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private List<Long> roles;
}
