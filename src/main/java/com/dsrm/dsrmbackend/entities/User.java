package com.dsrm.dsrmbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Integer roomNumber;
    private LocalDateTime banEnd;
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Reservation> reservations;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "keyOwner")
    private Set<Room> rooms;

    @PreRemove
    private void preRemove() {
        rooms.forEach(child -> child.setKeyOwner(null));
    }

}
