package com.example.cinemaapi.repository.cinema;

import com.example.cinemaapi.model.cinema.ShowTime;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;

/**
 * .
 */
public interface ShowTimeRepository extends CrudRepository<ShowTime, Long> {
}