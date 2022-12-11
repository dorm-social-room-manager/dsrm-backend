package com.dsrm.dsrmbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private Long id;
    private String name;
}
