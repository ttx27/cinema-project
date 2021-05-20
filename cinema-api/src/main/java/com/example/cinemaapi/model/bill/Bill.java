package com.example.cinemaapi.model.bill;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.cinema.ShowTime;
import com.example.cinemaapi.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

/**
 * .
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(
        name = "bill",
        indexes = @Index(
                name = "idx_bill_code",
                columnList = "code",
                unique = true
        )
)
public class Bill extends UserDateAudit {
    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Integer status;

    private Integer total;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User customerDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_time_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ShowTime showTimeDetail;

//    @OneToMany(mappedBy = "billDetail", cascade = CascadeType.ALL)
//    private Set<BillDetail> billDetails;
}
