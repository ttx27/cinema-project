package com.example.cinemaapi.service.bill;

import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.Set;

/**
 * .
 */
public interface BillService {
    Set<BillDto> getAllBills(Long userId, Boolean isAdmin);

    BillDto addBill(BillDto billDto, UserDto userDto);

    BillDto updateBill(BillDto billDto);

    void deleteBill(Long billID);
}
