package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.movie.ActorRepository;
import com.example.cinemaapi.repository.movie.DirectorRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.ACTOR;
import static com.example.cinemaapi.exception.EntityType.DIRECTOR;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class DirectorServiceImpl implements DirectorService {
    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Director> getDirectorByIds(List<Long> ids) {
        List<Director> directors = new ArrayList<>();
        for(Director director : directorRepository.findAll())
        {
            if(ids.contains(director.getId())){
                directors.add(director);
            }
        }
        return directors;
    }

    @Override
    public Set<DirectorDto> getAllDirectors() {
        return StreamSupport
                .stream(directorRepository.findAll().spliterator(), false)
                .map(director -> modelMapper.map(director, DirectorDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public DirectorDto addDirector(DirectorDto directorDto, UserDto userDto) {
        Director directorModel = new Director().setFullName(directorDto.getFullName());

        directorModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        directorModel.setCreatedBy(userDto.getId());

        directorRepository.save(directorModel);
        return modelMapper.map(directorModel, DirectorDto.class);
    }

    @Transactional
    public DirectorDto updateDirector(DirectorDto directorDto, UserDto userDto) {
        Optional<Director> director = directorRepository.findById(directorDto.getId());
        if (director != null) {
            Director directorModel = director.get();
            directorModel.setFullName(directorDto.getFullName());

            directorModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            directorModel.setModifiedBy(userDto.getId());

            return modelMapper.map(directorRepository.save(directorModel), DirectorDto.class);
        }
        throw exceptionWithId(DIRECTOR, ENTITY_NOT_FOUND, 2, directorDto.getId().toString());
    }

    @Transactional
    public void deleteDirector(Long directorID) {
        Optional<Director> director = directorRepository.findById(directorID);
        if (director != null && director.isPresent()) {
            Director directorModel = director.get();

            Iterable<Movie> movies = movieRepository.findAll();

            for (Movie movie: movies) {
                movie.getDirectors().removeIf(n -> n.getId() == directorModel.getId());
                movieRepository.save(movie);
            }

            directorRepository.deleteById(directorID);
        } else {
            throw exceptionWithId(DIRECTOR, ENTITY_NOT_FOUND, 2, directorID.toString());
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
