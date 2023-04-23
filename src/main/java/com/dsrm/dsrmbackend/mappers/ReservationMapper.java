package com.dsrm.dsrmbackend.mappers;


import com.dsrm.dsrmbackend.dto.ReservationDTO;
import com.dsrm.dsrmbackend.dto.ReservationRequestDTO;
import com.dsrm.dsrmbackend.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {RoomMapper.class, UserMapper.class})
public interface ReservationMapper {

    @Mapping(source = "from", target = "startTime")
    @Mapping(source = "to", target = "endTime")
    Reservation toReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationDTO toReservationDTO(Reservation reservation);
}
