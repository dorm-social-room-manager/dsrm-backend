package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.validation.annotations.ExistingRoom;
import com.dsrm.dsrmbackend.validation.annotations.UserExists;
import com.dsrm.dsrmbackend.validation.annotations.UserNotBanned;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
public class ReservationRequestDTO {
    @NotNull
    @ExistingRoom
    private String room;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    @NotNull
    private LocalDateTime from;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    @NotNull
    private LocalDateTime to;
    @NotNull
    @UserExists
    @UserNotBanned
    private String user;
}
