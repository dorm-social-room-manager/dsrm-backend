package com.dsrm.dsrmbackend.tables;

import com.dsrm.dsrmbackend.tables.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    @ManyToMany
    @JoinTable(name = "user_roles")
    private Set<Role> roles;
}
