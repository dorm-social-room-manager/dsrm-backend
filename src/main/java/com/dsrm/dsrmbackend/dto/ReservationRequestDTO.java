package com.dsrm.dsrmbackend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReservationRequestDTO {
    private long room;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private long user;
}
