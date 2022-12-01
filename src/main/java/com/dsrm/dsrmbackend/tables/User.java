package com.dsrm.dsrmbackend.tables;

import com.dsrm.dsrmbackend.tables.Role;
import javax.persistence.*;
import java.util.Set;

@Entity
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
