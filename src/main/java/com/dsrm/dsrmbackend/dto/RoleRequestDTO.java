package com.dsrm.dsrmbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class RoleRequestDTO {
    @NotBlank
    private String name;
}
