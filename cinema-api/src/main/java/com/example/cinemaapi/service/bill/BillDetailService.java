package com.example.cinemaapi.service.bill;

import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.Set;

/**
 * .
 */
public interface BillDetailService {
    Set<BillDetailDto> getAllBillDetails(Long userId, Boolean isAdmin);

    BillDetailDto addBillDetail(BillDetailDto billDetailDto, UserDto userDto);

    BillDetailDto updateBillDetail(BillDetailDto billDetailDto);

    void deleteBillDetail(Long billDetailID);
}
