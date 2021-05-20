package com.example.cinemaapi.model.bill;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.cinema.Seat;
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
        name = "bill_detail",
        indexes = @Index(
                name = "idx_bill_ticket_number",
                columnList = "ticket_number",
                unique = true
        )
)
public class BillDetail extends UserDateAudit {
    @Id
    @Column(name = "bill_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    private Integer status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Seat seatDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Bill billDetail;
}
