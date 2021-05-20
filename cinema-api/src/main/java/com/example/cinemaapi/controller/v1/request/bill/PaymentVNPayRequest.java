package com.example.cinemaapi.controller.v1.request.bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentVNPayRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String OrderInfo;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String OrderType;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String Amount;

    private String BankCode;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String Language;
}
