package com.example.cinemaapi.controller.v1.request.cinema;

import com.example.cinemaapi.model.cinema.Seat;
import com.example.cinemaapi.model.cinema.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String name;

    private Integer number;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long room;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long seatType;
}
