package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.MovieShiftDto;
import com.example.cinemaapi.dto.model.cinema.ShowTimeDto;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.MovieShift;
import com.example.cinemaapi.model.cinema.ShowTime;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.MovieShiftRepository;
import com.example.cinemaapi.repository.movie.DirectorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.*;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class MovieShiftServiceImpl implements MovieShiftService {
    @Autowired
    private MovieShiftRepository movieShiftRepository;

    @Autowired
    private ShowTimeService showTimeService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovieShift> getMovieShiftByIds(List<Long> ids) {
        List<MovieShift> movieShifts = new ArrayList<>();
        for(MovieShift movieShift : movieShiftRepository.findAll())
        {
            if(ids.contains(movieShift.getId())){
                movieShifts.add(movieShift);
            }
        }
        return movieShifts;
    }

    @Override
    public Set<MovieShiftDto> getAllMovieShifts() {
        return StreamSupport
                .stream(movieShiftRepository.findAll().spliterator(), false)
                .map(movieShift -> modelMapper.map(movieShift, MovieShiftDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public MovieShiftDto addMovieShift(MovieShiftDto movieShiftDto, UserDto userDto) {
        MovieShift movieShiftModel = new MovieShift().setTime(movieShiftDto.getTime());

        movieShiftModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        movieShiftModel.setCreatedBy(userDto.getId());

        movieShiftRepository.save(movieShiftModel);
        return modelMapper.map(movieShiftModel, MovieShiftDto.class);
    }

    @Transactional
    public MovieShiftDto updateMovieShift(MovieShiftDto movieShiftDto, UserDto userDto) {
        Optional<MovieShift> movieShift = movieShiftRepository.findById(movieShiftDto.getId());
        if (movieShift != null) {
            MovieShift movieShiftModel = movieShift.get();
            movieShiftModel.setTime(movieShiftDto.getTime());

            movieShiftModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            movieShiftModel.setModifiedBy(userDto.getId());

            return modelMapper.map(movieShiftRepository.save(movieShiftModel), MovieShiftDto.class);
        }
        throw exceptionWithId(MOVIESHIFT, ENTITY_NOT_FOUND, 2, movieShiftDto.getId().toString());
    }

    @Transactional
    public void deleteMovieShift(Long movieShiftID) {
        Optional<MovieShift> movieShift = movieShiftRepository.findById(movieShiftID);
        if (movieShift != null && movieShift.isPresent()) {
            MovieShift movieShiftModel = movieShift.get();
            Set<ShowTimeDto> showTimeDtos = showTimeService.getAllShowTimes();

            for (ShowTimeDto showTimeDto: showTimeDtos) {
                if (showTimeDto.getShifts() != null && showTimeDto.getShifts().contains(movieShiftModel)) {
                    showTimeDto.getShifts().remove(movieShiftModel);
                    showTimeService.updateShowTime(showTimeDto, null);
                }
            }

            movieShiftRepository.deleteById(movieShiftID);
        } else {
            throw exceptionWithId(MOVIESHIFT, ENTITY_NOT_FOUND, 2, movieShiftID.toString());
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
