package com.example.cinemaapi.controller.v1.request.movie;

import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.model.movie.Director;
import com.example.cinemaapi.model.movie.Language;
import com.example.cinemaapi.model.movie.Subtitle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRequest {
    private String description;

    private String image;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String name;

    private String releaseDate;

    private String duration;

    private String rating;

    private Integer status;

    private List<Long> actors;

    private List<Long> categories;

    private List<Long> directors;

    private List<Long> languages;

    private List<Long> subtitles;
}
