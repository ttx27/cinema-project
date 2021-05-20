package com.example.cinemaapi.dto.mapper;

import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.cinema.Seat;
import com.example.cinemaapi.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * .
 */
@Component
public class SeatMapper {

    public static SeatDto toSeatDto(Seat seat) {
        SeatDto seatDto = new SeatDto()
            .setId(seat.getId())
            .setName(seat.getName())
            .setNumber(seat.getNumber());

        if (seat.getSeatTypeDetail() != null) {
            seatDto.setSeatType(seat.getSeatTypeDetail());
        }

        if (seat.getRoomDetail() != null) {
            seatDto.setRoom(seat.getRoomDetail());
        }
        return seatDto;
    }

}
