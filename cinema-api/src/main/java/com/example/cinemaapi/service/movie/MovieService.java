package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface MovieService {

    //movie related methods
    Set<MovieDto> getAllMovies(Boolean isAdmin);

    MovieDto getMovieById(Long movieID);

    MovieDto addMovie(MovieDto movieDto, UserDto userDto);

    MovieDto updateMovie(MovieDto movieDto, UserDto userDto);

    void deleteMovie(Long movieID);
}
