package com.example.cinemaapi.dto.model.movie;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.movie.*;
import com.example.cinemaapi.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto implements Comparable {
    private Long id;

    private String description;

    private String image;

    private String name;

    private String releaseDate;

    private String duration;

    private String rating;

    private Integer status;

    private List<Actor> actors;

    private List<Category> categories;

    private List<Director> directors;

    private List<Language> languages;

    private List<Subtitle> subtitles;

    private Long createdBy;
    private String createdDate;
    private Long modifiedBy;
    private String modifiedDate;

    @Override
    public int compareTo(Object o) {
        return this.getId().compareTo(((MovieDto) o).getId());
    }
}
