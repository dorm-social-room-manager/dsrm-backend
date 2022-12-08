package com.dsrm.dsrmbackend.dto;


import com.dsrm.dsrmbackend.tables.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private List<Role> roles;
    private boolean isBanned;
    private LocalDateTime banEndDate;
}
