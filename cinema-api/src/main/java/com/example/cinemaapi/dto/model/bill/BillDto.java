package com.example.cinemaapi.dto.model.bill;

import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.ShowTime;
import com.example.cinemaapi.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
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
public class BillDto implements Comparable {
    private Long id;

    private String code;

    private Integer status;

    private Integer total;

    private User customer;

    private ShowTime showTime;

    private Set<BillDetailDto> billDetails;

    private Long createdBy;
    private String createdDate;
    private Long modifiedBy;
    private String modifiedDate;

    @Override
    public int compareTo(Object o) {
        return this.getId().compareTo(((BillDto) o).getId());
    }
}
