package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RoomDTO {
    private Integer roomNumber;
    private Integer floor;
    private RoomType roomType;
    private Integer maxCapacity;
    private User keyOwner;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalDate unavailabilityStartDate;
    private LocalDate unavailabilityEndDate;
}
