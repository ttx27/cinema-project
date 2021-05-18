package com.example.cinemaapi.controller.v1.request.cinema;

import com.example.cinemaapi.model.cinema.MovieRoom;
import com.example.cinemaapi.model.cinema.MovieShift;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowTimeRequest {
    @NotNull(message = "{constraints.NotEmpty.message}")
    private Instant movieDate;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String movieTime;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long movieRoom;

    private List<Long> shifts;
}
