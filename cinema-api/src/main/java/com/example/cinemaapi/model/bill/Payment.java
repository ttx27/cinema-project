package com.example.cinemaapi.model.bill;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.user.User;
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
        name = "payment"
)
public class Payment extends UserDateAudit {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    private Integer totalAmount;
}
