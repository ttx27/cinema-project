package com.example.cinemaapi.repository.bill;

import com.example.cinemaapi.model.bill.Bill;
import com.example.cinemaapi.model.bill.BillDetail;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.List;

/**
 * .
 */
public interface BillDetailRepository extends CrudRepository<BillDetail, Long> {
    List<BillDetail> findByBillDetailIdIn(List<Long> billDetailIds);
}
