package com.example.cinemaapi.service.bill;

import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.bill.Bill;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.repository.bill.BillDetailRepository;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import org.modelmapper.ModelMapper;
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

import static com.example.cinemaapi.exception.EntityType.BILLDETAIL;
import static com.example.cinemaapi.exception.EntityType.ROOM;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<BillDetailDto> getAllBillDetails(Long userId, Boolean isAdmin) {
        Set<BillDetailDto> billDetailDtos = StreamSupport
                .stream(billDetailRepository.findAll().spliterator(), false)
                .map(billDetail -> modelMapper.map(billDetail, BillDetailDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
        Set<BillDetailDto> billDetailDtoResults = new HashSet<BillDetailDto>();

        if (isAdmin) {
            return  billDetailDtos;
        }

        for (BillDetailDto billDetailDto: billDetailDtos)
        {
            Bill bill = billDetailDto.getBill();
            if (bill != null) {
                User customer = bill.getCustomerDetail();

                if (customer != null && customer.getId() == userId) {
                    billDetailDtoResults.add(billDetailDto);
                }
            }
        }

        return billDetailDtoResults;
    }

    @Override
    public BillDetailDto addBillDetail(BillDetailDto billDetailDto, UserDto userDto) {
        BillDetail billDetailModel = new BillDetail().setTicketNumber(billDetailDto.getTicketNumber())
                .setStatus(billDetailDto.getStatus())
                .setBillDetail(billDetailDto.getBill())
                .setSeatDetail(billDetailDto.getSeat());

        billDetailModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        billDetailModel.setCreatedBy(userDto.getId());

        billDetailRepository.save(billDetailModel);
        return modelMapper.map(billDetailModel, BillDetailDto.class);
    }

    @Transactional
    public BillDetailDto updateBillDetail(BillDetailDto billDetailDto) {
        Optional<BillDetail> billDetail = billDetailRepository.findById(billDetailDto.getId());
        if (billDetail != null) {
            BillDetail billDetailModel = billDetail.get();
            billDetailModel.setTicketNumber(billDetailDto.getTicketNumber())
                    .setStatus(billDetailDto.getStatus())
                    .setBillDetail(billDetailDto.getBill())
                    .setSeatDetail(billDetailDto.getSeat());;
            return modelMapper.map(billDetailRepository.save(billDetailModel), BillDetailDto.class);
        }
        throw exceptionWithId(BILLDETAIL, ENTITY_NOT_FOUND, 2, billDetailDto.getId().toString());
    }

    @Transactional
    public void deleteBillDetail(Long billDetailID) {
        Optional<BillDetail> billDetail = billDetailRepository.findById(billDetailID);
        if (billDetail != null) {
            billDetailRepository.deleteById(billDetailID);
        } else {
            throw exceptionWithId(BILLDETAIL, ENTITY_NOT_FOUND, 2, billDetailID.toString());
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
