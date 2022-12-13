package com.dsrm.dsrmbackend.dto;

import com.dsrm.dsrmbackend.entities.Room;
import com.dsrm.dsrmbackend.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private Room room;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user;
}
