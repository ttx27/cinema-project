package com.example.cinemaapi.service.movie;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Actor;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface ActorService {
    List<Actor> getActorByIds(List<Long> ids);

    Set<ActorDto> getAllActors();

    ActorDto addActor(ActorDto actorDto, UserDto userDto);

    ActorDto updateActor(ActorDto actorDto, UserDto userDto);

    void deleteActor(Long actorID);
}
