package com.dsrm.dsrmbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime openingTime;
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime closingTime;
}
