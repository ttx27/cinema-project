package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.MovieRoomDto;
import com.example.cinemaapi.dto.model.cinema.ShowTimeDto;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.MovieRoom;
import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.MovieRoomRepository;
import com.example.cinemaapi.repository.movie.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.CATEGORY;
import static com.example.cinemaapi.exception.EntityType.MOVIEROOM;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class MovieRoomServiceImpl implements MovieRoomService {
    @Autowired
    private MovieRoomRepository movieRoomRepository;

    @Autowired
    private ShowTimeService showTimeService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<MovieRoomDto> getAllMovieRooms() {
        return StreamSupport
                .stream(movieRoomRepository.findAll().spliterator(), false)
                .map(movieRoom -> modelMapper.map(movieRoom, MovieRoomDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public MovieRoomDto addMovieRoom(MovieRoomDto movieRoomDto, UserDto userDto) {
        MovieRoom movieRoomModel = new MovieRoom()
                .setMovieDetail(movieRoomDto.getMovie())
                .setRoomDetail(movieRoomDto.getRoom());

        movieRoomModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        movieRoomModel.setCreatedBy(userDto.getId());

        movieRoomRepository.save(movieRoomModel);
        return modelMapper.map(movieRoomModel, MovieRoomDto.class);
    }

    @Transactional
    public MovieRoomDto updateMovieRoom(MovieRoomDto movieRoomDto, UserDto userDto) {
        Optional<MovieRoom> movieRoom = movieRoomRepository.findById(movieRoomDto.getId());
        if (movieRoom != null) {
            MovieRoom movieRoomModel = movieRoom.get();
            movieRoomModel
                .setMovieDetail(movieRoomDto.getMovie())
                .setRoomDetail(movieRoomDto.getRoom());

            movieRoomModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            movieRoomModel.setModifiedBy(userDto.getId());

            return modelMapper.map(movieRoomRepository.save(movieRoomModel), MovieRoomDto.class);
        }
        throw exceptionWithId(MOVIEROOM, ENTITY_NOT_FOUND, 2, movieRoomDto.getId().toString());
    }

    @Transactional
    public void deleteMovieRoom(Long movieRoomID) {
        Optional<MovieRoom> movieRoom = movieRoomRepository.findById(movieRoomID);
        if (movieRoom != null && movieRoom.isPresent()) {
            Set<ShowTimeDto> showTimeDtos = showTimeService.getAllShowTimes();

            for (ShowTimeDto showTimeDto: showTimeDtos) {
                if (showTimeDto.getMovieRoom() != null && showTimeDto.getMovieRoom().getId() == movieRoomID) {
                    showTimeService.deleteShowTime(showTimeDto.getId());
                }
            }

            movieRoomRepository.deleteById(movieRoomID);
        } else {
            throw exceptionWithId(MOVIEROOM, ENTITY_NOT_FOUND, 2, movieRoomID.toString());
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
