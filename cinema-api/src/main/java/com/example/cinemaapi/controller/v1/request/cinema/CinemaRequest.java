package com.example.cinemaapi.controller.v1.request.cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CinemaRequest {
    private String address;

    private String description;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String name;

    private String phone;

    private Integer status;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String city;

    private Integer cityId;
}
