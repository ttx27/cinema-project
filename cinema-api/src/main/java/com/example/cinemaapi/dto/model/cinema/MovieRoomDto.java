package com.example.cinemaapi.dto.model.cinema;

import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

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
public class MovieRoomDto implements Comparable {
    private Long id;

    private Movie movie;

    private Room room;

    private Long createdBy;
    private String createdDate;
    private Long modifiedBy;
    private String modifiedDate;

    @Override
    public int compareTo(Object o) {
        return this.getId().compareTo(((MovieRoomDto) o).getId());
    }
}
