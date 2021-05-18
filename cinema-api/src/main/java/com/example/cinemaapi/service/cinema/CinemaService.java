package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Actor;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface CinemaService {
    Set<CinemaDto> getAllCinemas();

    CinemaDto addCinema(CinemaDto cinemaDto, UserDto userDto);

    CinemaDto updateCinema(CinemaDto cinemaDto, UserDto userDto);

    void deleteCinema(Long cinemaID);
}
