package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDTO {
    private RoomDTO room;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    private LocalDateTime from;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(type="string")
    private LocalDateTime to;
    private UserDTO user;
}
