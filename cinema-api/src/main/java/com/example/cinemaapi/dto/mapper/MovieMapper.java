package com.example.cinemaapi.dto.mapper;

import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * .
 */
@Component
public class MovieMapper {

    public static MovieDto toMovieDto(Movie movie) {
        return new MovieDto()
                .setId(movie.getId())
                .setName(movie.getName())
                .setImage(movie.getImage())
                .setDescription(movie.getDescription())
                .setDuration(movie.getDuration())
                .setRating(movie.getRating())
                .setStatus(movie.getStatus())
                .setReleaseDate(movie.getReleaseDate());
    }

}
