package com.dsrm.dsrmbackend.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private Long id;
    private String name;
}
