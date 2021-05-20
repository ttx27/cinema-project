package com.example.cinemaapi.service.bill;

import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.bill.Bill;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.repository.bill.BillRepository;
import com.example.cinemaapi.repository.cinema.CinemaRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.BILL;
import static com.example.cinemaapi.exception.EntityType.CINEMA;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class BillServiceImpl implements BillService {
    Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<BillDto> getAllBills(Long userId, Boolean isAdmin) {
        Set<BillDto> billDtos = StreamSupport
                .stream(billRepository.findAll().spliterator(), false)
                .map(bill -> modelMapper.map(bill, BillDto.class))
                .collect(Collectors.toCollection(TreeSet::new));

        Set<BillDto> billDtoResults = new HashSet<BillDto>();

        if (isAdmin) {
            return  billDtos;
        }

        for (BillDto billDto: billDtos)
        {
            User customer = billDto.getCustomer();

            if (customer != null && customer.getId() == userId) {
                billDtoResults.add(billDto);
            }
        }

        return billDtoResults;
    }

    @Override
    public BillDto addBill(BillDto billDto, UserDto userDto) {
        Bill billModel = new Bill().setCode(billDto.getCode())
            .setStatus(billDto.getStatus())
            .setCustomerDetail(billDto.getCustomer())
            .setTotal(billDto.getTotal())
            .setShowTimeDetail(billDto.getShowTime());

        billModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        billModel.setCreatedBy(userDto.getId());

        billRepository.save(billModel);
        return modelMapper.map(billModel, BillDto.class);
    }

    @Transactional
    public BillDto updateBill(BillDto billDto) {
        Optional<Bill> bill = billRepository.findById(billDto.getId());
        if (bill != null) {
            Bill billModel = bill.get();
            billModel.setCode(billDto.getCode())
                .setStatus(billDto.getStatus())
                .setCustomerDetail(billDto.getCustomer())
                .setTotal(billDto.getTotal())
                .setShowTimeDetail(billDto.getShowTime());
            return modelMapper.map(billRepository.save(billModel), BillDto.class);
        }
        throw exceptionWithId(BILL, ENTITY_NOT_FOUND, 2, billDto.getId().toString());
    }

    @Transactional
    public void deleteBill(Long billID) {
        Optional<Bill> bill = billRepository.findById(billID);
        if (bill != null) {
            billRepository.deleteById(billID);
        } else {
            throw exceptionWithId(BILL, ENTITY_NOT_FOUND, 2, billID.toString());
        }
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BRSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
