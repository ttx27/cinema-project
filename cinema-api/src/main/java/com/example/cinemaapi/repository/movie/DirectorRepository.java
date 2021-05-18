package com.example.cinemaapi.repository.movie;

import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Director;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * .
 */
public interface DirectorRepository extends CrudRepository<Director, Long> {
}