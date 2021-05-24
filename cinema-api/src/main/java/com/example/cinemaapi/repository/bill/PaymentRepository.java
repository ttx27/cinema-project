package com.example.cinemaapi.repository.bill;

import com.example.cinemaapi.model.bill.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * .
 */
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
