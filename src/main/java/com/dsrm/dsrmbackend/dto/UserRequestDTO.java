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
    @NotBlank(message = "field.not.blank")
    private String email;
    @NotBlank(message = "field.not.blank")
    private String password;
    @NotBlank(message = "field.not.blank")
    private String name;
    @NotBlank(message = "field.not.blank")
    private String surname;
    @NotNull(message = "field.not.null")
    private Integer roomNumber;
    private List<String> roles;
}
