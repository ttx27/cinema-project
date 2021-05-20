package com.example.cinemaapi.service.bill;

import com.example.cinemaapi.dto.model.bill.BillDto;

import java.util.Set;

/**
 * .
 */
public interface PaymentService {
    Set<BillDto> getAllBills();

    BillDto addBill(BillDto billDto);

    BillDto updateBill(BillDto billDto);

    void deleteBill(Long billID);
}
