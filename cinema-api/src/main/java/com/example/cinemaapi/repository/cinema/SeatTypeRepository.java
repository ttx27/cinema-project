package com.example.cinemaapi.repository.cinema;

import com.example.cinemaapi.model.cinema.SeatType;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * .
 */
public interface SeatTypeRepository extends CrudRepository<SeatType, Long> {
}