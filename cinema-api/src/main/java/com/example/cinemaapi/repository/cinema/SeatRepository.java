package com.example.cinemaapi.repository.cinema;

import com.example.cinemaapi.model.cinema.Seat;
import com.example.cinemaapi.model.cinema.SeatType;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * .
 */
public interface SeatRepository extends CrudRepository<Seat, Long> {
//    Set<SeatType> findAll();
}
