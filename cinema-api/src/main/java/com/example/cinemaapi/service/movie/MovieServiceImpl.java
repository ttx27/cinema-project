package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.mapper.MovieMapper;
import com.example.cinemaapi.dto.model.cinema.MovieRoomDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.MovieRoom;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.MovieRoomRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.security.api.ApiJWTAuthorizationFilter;
import com.example.cinemaapi.service.cinema.MovieRoomService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.*;
import static com.example.cinemaapi.exception.ExceptionType.*;

/**
 * .
 */
@Component
public class MovieServiceImpl implements MovieService {
    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieRoomService movieRoomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<MovieDto> getAllMovies(Boolean isAdmin) {
        Set<MovieDto> movies = new HashSet<MovieDto>();

        Set<MovieDto> movieList = StreamSupport
                .stream(movieRepository.findAll().spliterator(), false)
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toCollection(TreeSet::new));

        if (!isAdmin) {
            for (MovieDto movie: movieList) {
                if (movie.getStatus() == 1) {
                    movies.add(movie);
                }
            }
        } else  {
            movies = movieList;
        }

        return movies;
    }

    @Override
    public MovieDto getMovieById(Long movieID) {
        Optional<Movie> movie = movieRepository.findById(movieID);
        if (movie.isPresent()) {
            return MovieMapper.toMovieDto(movie.get());
        }
        throw exception(MOVIE, ENTITY_NOT_FOUND, movieID.toString());
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, UserDto userDto) {
        Movie movieModel = new Movie()
                .setName(movieDto.getName())
                .setDescription(movieDto.getDescription())
                .setImage(movieDto.getImage())
                .setReleaseDate(movieDto.getReleaseDate())
                .setDuration(movieDto.getDuration())
                .setRating(movieDto.getRating())
                .setStatus(movieDto.getStatus())
                .setActors(movieDto.getActors())
                .setDirectors(movieDto.getDirectors())
                .setCategories(movieDto.getCategories())
                .setLanguages(movieDto.getLanguages())
                .setSubtitles(movieDto.getSubtitles());

        movieModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        movieModel.setCreatedBy(userDto.getId());

        movieRepository.save(movieModel);
        return modelMapper.map(movieModel, MovieDto.class);
    }

    @Transactional
    public MovieDto updateMovie(MovieDto movieDto, UserDto userDto) {
        Optional<Movie> movie = movieRepository.findById(movieDto.getId());
        if (movie != null) {
            Movie movieModel = movie.get();
            if (!movieDto.getName().isEmpty()) {
                movieModel.setName(movieDto.getName());
            }
            if (!movieDto.getDescription().isEmpty()) {
                movieModel.setDescription(movieDto.getDescription());
            }
            if (!movieDto.getImage().isEmpty()) {
                movieModel.setImage(movieDto.getImage());
            }
            if (!movieDto.getReleaseDate().isEmpty()) {
                movieModel.setReleaseDate(movieDto.getReleaseDate());
            }
            if (!movieDto.getDuration().isEmpty()) {
                movieModel.setDuration(movieDto.getDuration());
            }
            if (!movieDto.getRating().isEmpty()) {
                movieModel.setRating(movieDto.getRating());
            }
            if (!movieDto.getStatus().toString().isEmpty()) {
                movieModel.setStatus(movieDto.getStatus());
            }

            if (movieDto.getActors() != null && !movieDto.getActors().isEmpty()) {
                movieModel.setActors(movieDto.getActors());
            }

            if (movieDto.getCategories() != null && !movieDto.getCategories().isEmpty()) {
                movieModel.setCategories(movieDto.getCategories());
            }

            if (movieDto.getDirectors() != null && !movieDto.getDirectors().isEmpty()) {
                movieModel.setDirectors(movieDto.getDirectors());
            }

            if (movieDto.getLanguages() != null && !movieDto.getLanguages().isEmpty()) {
                movieModel.setLanguages(movieDto.getLanguages());
            }

            if (movieDto.getSubtitles() != null && !movieDto.getSubtitles().isEmpty()) {
                movieModel.setSubtitles(movieDto.getSubtitles());
            }

            movieModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            movieModel.setModifiedBy(userDto.getId());

            return modelMapper.map(movieRepository.save(movieModel), MovieDto.class);
        }

        throw exceptionWithId(MOVIE, ENTITY_NOT_FOUND, 2, movieDto.getId().toString());
    }

    @Transactional
    public void deleteMovie(Long movieID) {
        Optional<Movie> movie = movieRepository.findById(movieID);
        if (movie != null && movie.isPresent()) {
            Movie movieModel = movie.get();

            Set<MovieRoomDto> movieRoomDtos = movieRoomService.getAllMovieRooms();

            for (MovieRoomDto movieRoomDto: movieRoomDtos) {
                if (movieRoomDto.getMovie() != null && movieRoomDto.getMovie().getId() == movieID) {
                    movieRoomService.deleteMovieRoom(movieRoomDto.getId());
                }
            }

            movieRepository.deleteById(movieID);
        } else {
            throw exceptionWithId(MOVIE, ENTITY_NOT_FOUND, 2, movieID.toString());
        }
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BRSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
