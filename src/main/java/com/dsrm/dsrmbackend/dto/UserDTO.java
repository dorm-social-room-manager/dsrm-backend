package com.dsrm.dsrmbackend.dto;


import com.dsrm.dsrmbackend.entities.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String id;
    private String email;
    private String name;
    private String surname;
    private List<Role> roles;
    private boolean isBanned;
    private Integer roomNumber;
    private LocalDateTime banEndDate;
}
