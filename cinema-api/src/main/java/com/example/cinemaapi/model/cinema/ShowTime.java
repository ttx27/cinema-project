package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "show_time"
)
public class ShowTime extends UserDateAudit {
    @Id
    @Column(name = "show_time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_date")
    private Instant movieDate;

    @Column(name = "movie_time")
    private String movieTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_room_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private MovieRoom movieRoomDetail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "showtime_movieshift",
            joinColumns = {@JoinColumn(name = "show_time_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_shift_id")})
    private List<MovieShift> shifts;
}