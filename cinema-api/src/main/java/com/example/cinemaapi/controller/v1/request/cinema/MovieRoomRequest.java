package com.example.cinemaapi.controller.v1.request.cinema;

import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRoomRequest {
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long movie;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long room;
}
