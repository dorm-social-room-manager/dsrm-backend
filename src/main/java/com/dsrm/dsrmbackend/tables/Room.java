package com.dsrm.dsrmbackend.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room {
    @Id
    private Long Id;
    @Column(name = "room_number")
    private Integer roomNumber;
    private Integer floor;
    @OneToMany
    @JoinColumn(name = "type_id")
    private Set<RoomType> roomType;
    @Column(name = "max_capacity")
    private Integer maxCapacity;
    @OneToMany
    @JoinColumn(name = "key_owner_id")
    @Column(name = "key_owner_id")
    private Set<User> keyOwner;
    @Column(name = "opening_time")
    private LocalTime openingTime;
    @Column(name = "closing_time")
    private LocalTime closingTime;
    @Column(name = "unavailability_start_date")
    private LocalDate unavailabilityStartDate;
    @Column(name = "unavailability_end_date")
    private LocalDate unavailabilityEndDate;
}
