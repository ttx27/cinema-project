package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.movie.ActorRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
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

import static com.example.cinemaapi.exception.EntityType.ACTOR;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class ActorServiceImpl implements ActorService {
    Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Actor> getActorByIds(List<Long> ids) {
        List<Actor> actors = new ArrayList<>();
        for(Actor actor : actorRepository.findAll())
        {
            if(ids.contains(actor.getId())){
                actors.add(actor);
            }
        }
        return actors;
    }

    @Override
    public Set<ActorDto> getAllActors() {
        return StreamSupport
                .stream(actorRepository.findAll().spliterator(), false)
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public ActorDto addActor(ActorDto actorDto, UserDto userDto) {
        Actor actorModel = new Actor().setFullName(actorDto.getFullName());

        actorModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        actorModel.setCreatedBy(userDto.getId());

        actorRepository.save(actorModel);
        return modelMapper.map(actorModel, ActorDto.class);
    }

    @Transactional
    public ActorDto updateActor(ActorDto actorDto, UserDto userDto) {
        Optional<Actor> actor = actorRepository.findById(actorDto.getId());
        if (actor != null) {
            Actor actorModel = actor.get();
            actorModel.setFullName(actorDto.getFullName());

            actorModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            actorModel.setModifiedBy(userDto.getId());

            return modelMapper.map(actorRepository.save(actorModel), ActorDto.class);
        }
        throw exceptionWithId(ACTOR, ENTITY_NOT_FOUND, 2, actorDto.getId().toString());
    }

    @Transactional
    public void deleteActor(Long actorID) {
        Optional<Actor> actor = actorRepository.findById(actorID);
        if (actor != null && actor.isPresent()) {
            Actor actorModel = actor.get();

            Iterable<Movie> movies = movieRepository.findAll();

            for (Movie movie: movies) {
                movie.getActors().removeIf(n -> n.getId() == actorModel.getId());
                movieRepository.save(movie);
            }

            actorRepository.deleteById(actorID);
        } else {
            throw exceptionWithId(ACTOR, ENTITY_NOT_FOUND, 2, actorID.toString());
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
