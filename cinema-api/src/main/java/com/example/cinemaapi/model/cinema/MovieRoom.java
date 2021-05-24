package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.movie.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
        name = "movie_room"
)
public class MovieRoom extends UserDateAudit {
    @Id
    @Column(name = "movie_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Movie movieDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Room roomDetail;
}
