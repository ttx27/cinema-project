package com.example.cinemaapi.repository.bill;

import com.example.cinemaapi.model.bill.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * .
 */
public interface BillRepository extends CrudRepository<Bill, Long> {
    List<Bill> findAllByShowTimeDetailId(Long showTimeDetailId);

    Bill findByCode(String code);
}