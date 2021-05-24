package com.example.cinemaapi.model.user;

import com.example.cinemaapi.model.audit.UserDateAudit;
//import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.bill.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * .
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users",
        indexes = {
            @Index(
                name = "idx_user_email",
                columnList = "email",
                unique = true
            )
        })
public class User extends UserDateAudit {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(name = "fullname")
    private String fullName;

    private Integer status;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "verify_code")
    private String verifyCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Payment payment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;
}
