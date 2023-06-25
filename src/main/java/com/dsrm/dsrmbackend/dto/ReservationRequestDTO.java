package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.validation.annotations.NotBeforeDateTime;
import com.dsrm.dsrmbackend.validation.annotations.NotPastDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@NotBeforeDateTime
public class ReservationRequestDTO {
    @NotNull
    private String room;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    @NotNull
    @NotPastDateTime
    private LocalDateTime from;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    @NotNull
    private LocalDateTime to;
    @NotNull
    private String user;
}
