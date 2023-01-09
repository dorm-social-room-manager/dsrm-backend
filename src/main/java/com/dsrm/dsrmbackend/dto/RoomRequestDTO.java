package com.dsrm.dsrmbackend.dto;

import lombok.*;

import java.time.LocalTime;


@Getter
@Setter
public class RoomRequestDTO {
    private String name;
    private int number;
    private int floor;
    private long type;
    private int maxCapacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
