package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * .
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "movie_shift"
)
public class MovieShift extends UserDateAudit {
    @Id
    @Column(name = "movie_shift_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer time;
}