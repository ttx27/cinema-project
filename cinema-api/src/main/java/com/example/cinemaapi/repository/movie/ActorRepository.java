package com.example.cinemaapi.repository.movie;

import com.example.cinemaapi.model.movie.Actor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * .
 */
public interface ActorRepository extends CrudRepository<Actor, Long> {
}