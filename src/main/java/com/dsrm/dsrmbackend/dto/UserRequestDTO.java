package com.dsrm.dsrmbackend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private Integer roomNumber;
    private List<String> roles;
}
