package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.SeatTypeDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Subtitle;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface SeatTypeService {
    Set<SeatTypeDto> getAllSeatTypes();

    SeatTypeDto addSeatType(SeatTypeDto seatTypeDto, UserDto userDto);

    SeatTypeDto updateSeatType(SeatTypeDto seatTypeDto, UserDto userDto);

    void deleteSeatType(Long seatTypeID);
}
