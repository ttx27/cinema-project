package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.SeatTypeDto;
import com.example.cinemaapi.dto.model.cinema.ShowTimeDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.Set;

/**
 * .
 */
public interface ShowTimeService {
    Set<ShowTimeDto> getAllShowTimes();

    ShowTimeDto addShowTime(ShowTimeDto showTimeDto, UserDto userDto);

    ShowTimeDto updateShowTime(ShowTimeDto showTimeDto, UserDto userDto);

    void deleteShowTime(Long showTimeID);
}
