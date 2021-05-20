package com.example.cinemaapi.model.cinema;

import com.example.cinemaapi.model.audit.UserDateAudit;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
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
        name = "cinema"
)
public class Cinema extends UserDateAudit {
    @Id
    @Column(name = "cinema_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String description;

    private String name;

    @Length(max = 11)
    private String phone;

    private Integer status;

    private String city;

    private Integer cityId;

//    @OneToMany(mappedBy = "cinemaDetail", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Room> rooms;
}
