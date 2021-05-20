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
        name = "category"
)
public class Category extends UserDateAudit {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private int status;
}
