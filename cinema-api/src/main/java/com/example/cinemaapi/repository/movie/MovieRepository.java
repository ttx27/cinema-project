package com.example.cinemaapi.repository.movie;

import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.UserRoles;
import org.springframework.data.repository.CrudRepository;

/**
 * .
 */
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
