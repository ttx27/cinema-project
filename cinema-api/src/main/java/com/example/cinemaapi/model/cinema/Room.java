package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.bill.Bill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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
        name = "room"
)
public class Room extends UserDateAudit {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer status;

//    @OneToMany(mappedBy = "roomDetail", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private Set<Seat> seats;

    //    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Cinema cinemaDetail;
}
