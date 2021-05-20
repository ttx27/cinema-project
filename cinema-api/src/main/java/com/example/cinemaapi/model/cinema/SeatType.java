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
        name = "seat_type"
)
public class SeatType extends UserDateAudit {
    @Id
    @Column(name = "seat_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;
}
