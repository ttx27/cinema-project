package com.example.cinemaapi.controller.v1.request.cinema;

import com.example.cinemaapi.model.cinema.Cinema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
public class RoomRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String name;

    private String description;

    private Integer status;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long cinema;
}
