package com.dsrm.dsrmbackend.DTOobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private List<Long> roles;
}
