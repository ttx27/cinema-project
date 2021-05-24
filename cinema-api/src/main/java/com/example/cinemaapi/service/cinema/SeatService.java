package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.Set;

/**
 * .
 */
public interface SeatService {
    Set<SeatDto> getAllSeats();

    SeatDto addSeat(SeatDto seatDto, UserDto userDto);

    SeatDto updateSeat(SeatDto seatDto, UserDto userDto);

    void deleteSeat(Long seatID);
}
