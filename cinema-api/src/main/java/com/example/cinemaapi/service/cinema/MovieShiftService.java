package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.MovieShiftDto;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.cinema.MovieShift;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Director;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface MovieShiftService {
    List<MovieShift> getMovieShiftByIds(List<Long> ids);

    Set<MovieShiftDto> getAllMovieShifts();

    MovieShiftDto addMovieShift(MovieShiftDto movieShiftDto, UserDto userDto);

    MovieShiftDto updateMovieShift(MovieShiftDto movieShiftDto, UserDto userDto);

    void deleteMovieShift(Long movieShiftID);
}
