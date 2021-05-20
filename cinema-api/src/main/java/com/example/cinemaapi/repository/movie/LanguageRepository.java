package com.example.cinemaapi.repository.movie;

import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * .
 */
public interface LanguageRepository extends CrudRepository<Language, Long> {
}