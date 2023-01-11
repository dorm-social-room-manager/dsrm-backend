package com.dsrm.dsrmbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "reservations")
@Getter
@Setter
public class Reservation {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
