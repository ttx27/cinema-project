package com.example.cinemaapi.controller.v1.request;

import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String email;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private String password;

    private String fullName;

    private Integer status;

    private List<Long> roles;
}
