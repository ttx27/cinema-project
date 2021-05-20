package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.MovieRoomDto;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Category;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface MovieRoomService {
    Set<MovieRoomDto> getAllMovieRooms();

    MovieRoomDto addMovieRoom(MovieRoomDto movieRoomDto, UserDto userDto);

    MovieRoomDto updateMovieRoom(MovieRoomDto movieRoomDto, UserDto userDto);

    void deleteMovieRoom(Long movieRoomID);
}
