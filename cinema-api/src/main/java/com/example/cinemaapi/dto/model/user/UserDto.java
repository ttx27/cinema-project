package com.example.cinemaapi.dto.model.user;

//import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Set;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Comparable {
    private Long id;
    private String email;
    private String token;
    private String password;
    private String fullName;
    private Integer status;
    private String recoveryCode;
    private String verifyCode;
    private PaymentDto payment;
    private boolean isAdmin;
    private Set<RoleDto> roles;

    private Long createdBy;
    private String createdDate;
    private Long modifiedBy;
    private String modifiedDate;

    @Override
    public int compareTo(Object o) {
        return this.getId().compareTo(((UserDto) o).getId());
    }
}
