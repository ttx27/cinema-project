package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.cinema.SeatTypeDto;
import com.example.cinemaapi.dto.model.cinema.ShowTimeDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.SeatType;
import com.example.cinemaapi.model.cinema.ShowTime;
import com.example.cinemaapi.repository.cinema.SeatTypeRepository;
import com.example.cinemaapi.repository.cinema.ShowTimeRepository;
import com.example.cinemaapi.service.bill.BillDetailService;
import com.example.cinemaapi.service.bill.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.SEATTYPE;
import static com.example.cinemaapi.exception.EntityType.SHOWTIME;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<ShowTimeDto> getAllShowTimes() {
        return StreamSupport
                .stream(showTimeRepository.findAll().spliterator(), false)
                .map(showTime -> modelMapper.map(showTime, ShowTimeDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public ShowTimeDto addShowTime(ShowTimeDto showTimeDto, UserDto userDto) {
        ShowTime showTimeModel = new ShowTime().setMovieTime(showTimeDto.getMovieTime())
                .setMovieDate(showTimeDto.getMovieDate())
                .setMovieRoomDetail(showTimeDto.getMovieRoom())
                .setShifts(showTimeDto.getShifts());

        showTimeModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        showTimeModel.setCreatedBy(userDto.getId());

        showTimeRepository.save(showTimeModel);
        return modelMapper.map(showTimeModel, ShowTimeDto.class);
    }

    @Transactional
    public ShowTimeDto updateShowTime(ShowTimeDto showTimeDto, UserDto userDto) {
        Optional<ShowTime> showTime = showTimeRepository.findById(showTimeDto.getId());
        if (showTime != null) {
            ShowTime showTimeModel = showTime.get();
            showTimeModel.setMovieTime(showTimeDto.getMovieTime())
                    .setMovieDate(showTimeDto.getMovieDate())
                    .setMovieRoomDetail(showTimeDto.getMovieRoom())
                    .setShifts(showTimeDto.getShifts());

            showTimeModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            if (userDto != null) {
                showTimeModel.setModifiedBy(userDto.getId());
            }

            return modelMapper.map(showTimeRepository.save(showTimeModel), ShowTimeDto.class);
        }
        throw exceptionWithId(SHOWTIME, ENTITY_NOT_FOUND, 2, showTimeDto.getId().toString());
    }

    @Transactional
    public void deleteShowTime(Long showTimeID) {
        Optional<ShowTime> showTime = showTimeRepository.findById(showTimeID);
        if (showTime != null) {
            Set<BillDto> billDtos = billService.getAllBills(null, true);

            for (BillDto billDto: billDtos) {
                if (billDto.getShowTime() != null && billDto.getShowTime().getId() == showTimeID) {
                    billDto.setShowTime(null);
                    billService.updateBill(billDto);
                }
            }

            showTimeRepository.deleteById(showTimeID);
        } else {
            throw exceptionWithId(SHOWTIME, ENTITY_NOT_FOUND, 2, showTimeID.toString());
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
