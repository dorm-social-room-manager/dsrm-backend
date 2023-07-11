package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.validators.UserRolesConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
public class UserRolesOnlyDTO {
    @UserRolesConstraint
    private List<String> roles;
}
