package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.RoomType;
import com.dsrm.dsrmbackend.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string", pattern = "HH:mm:ss")
    private LocalTime openingTime;
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string", pattern = "HH:mm:ss")
    private LocalTime closingTime;
    private LocalDate unavailableStart;
    private LocalDate unavailableEnd;
}
