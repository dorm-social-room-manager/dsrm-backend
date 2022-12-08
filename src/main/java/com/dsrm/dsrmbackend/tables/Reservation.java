package com.dsrm.dsrmbackend.tables;

import com.dsrm.dsrmbackend.tables.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity(name = "reservations")
@Getter
@Setter
public class Reservation {
    @Id
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<User> user;
}
