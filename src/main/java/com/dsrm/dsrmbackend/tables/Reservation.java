package com.dsrm.dsrmbackend.tables;

import com.dsrm.dsrmbackend.tables.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    private Long Id;
    @OneToMany
    @JoinColumn(name = "room_id")
    private Set<Room> room;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<User> user;
}
