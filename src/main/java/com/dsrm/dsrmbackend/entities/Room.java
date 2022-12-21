package com.dsrm.dsrmbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "rooms")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    @Column(name = "room_number")
    private Integer roomNumber;
    private Integer floor;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomType roomType;
    @Column(name = "max_capacity")
    private Integer maxCapacity;
    @ManyToOne
    @JoinColumn(name = "key_owner_id")
    private User keyOwner;
    @Column(name = "opening_time")
    private LocalTime openingTime;
    @Column(name = "closing_time")
    private LocalTime closingTime;
    @Column(name = "unavailability_start_date")
    private LocalDate unavailabilityStartDate;
    @Column(name = "unavailability_end_date")
    private LocalDate unavailabilityEndDate;
}
