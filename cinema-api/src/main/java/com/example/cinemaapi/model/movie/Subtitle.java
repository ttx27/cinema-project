package com.example.cinemaapi.model.movie;

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
        name = "subtitle"
)
public class Subtitle extends UserDateAudit {
    @Id
    @Column(name = "subtitle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subtitle;
}
