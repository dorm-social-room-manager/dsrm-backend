package com.dsrm.dsrmbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;


@Getter
@Setter
public class RoomRequestDTO {
    @NotBlank
    private String name;
    @NotNull
    private Integer number;
    @NotNull
    private Integer floor;
    private String type;
    @NotNull
    private Integer maxCapacity;
    @NotNull
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime openingTime;
    @NotNull
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalTime closingTime;
}
