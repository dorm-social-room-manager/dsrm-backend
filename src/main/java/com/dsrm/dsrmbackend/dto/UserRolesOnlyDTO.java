package com.dsrm.dsrmbackend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
public class UserRolesOnlyDTO {
    private List<Long> roles;
}
