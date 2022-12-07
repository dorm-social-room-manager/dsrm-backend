package com.dsrm.dsrmbackend.DTOobjects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private List<Long> roles;
}
