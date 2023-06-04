package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @NotNull
    private String keyOwner;
    @NotNull
    private String type;
    @NotNull
    private Integer maxCapacity;
    @NotNull
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string", pattern = "HH:mm:ss")
    private LocalTime openingTime;
    @NotNull
    @JsonFormat(pattern="HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string", pattern = "HH:mm:ss")
    private LocalTime closingTime;
}
