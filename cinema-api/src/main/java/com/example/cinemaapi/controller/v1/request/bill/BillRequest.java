package com.example.cinemaapi.controller.v1.request.bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequest {
    private Integer total;

    private List<Long> seats;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long showTime;

    @NotNull(message = "{constraints.NotEmpty.message}")
    private Long customer;
}
