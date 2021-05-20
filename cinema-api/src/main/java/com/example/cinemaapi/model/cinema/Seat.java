package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        name = "seat"
)
public class Seat extends UserDateAudit {
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Room roomDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_type_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private SeatType seatTypeDetail;
}
