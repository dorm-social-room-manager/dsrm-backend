package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDTO {
    private Room room;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private User user;
}
