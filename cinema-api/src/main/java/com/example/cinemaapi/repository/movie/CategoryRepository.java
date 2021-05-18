package com.example.cinemaapi.repository.movie;

import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * .
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}