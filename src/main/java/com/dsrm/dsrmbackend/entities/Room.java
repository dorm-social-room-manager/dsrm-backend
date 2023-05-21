package com.dsrm.dsrmbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String id;
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
    @Column(name = "unavailable_start")
    private LocalDate unavailableStart;
    @Column(name = "unavailable_end")
    private LocalDate unavailableEnd;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Reservation> reservationList;
}
