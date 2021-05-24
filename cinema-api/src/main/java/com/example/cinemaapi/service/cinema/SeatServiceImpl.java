package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.mapper.MovieMapper;
import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.Seat;
import com.example.cinemaapi.model.cinema.SeatType;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.SeatRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.service.bill.BillDetailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.MOVIE;
import static com.example.cinemaapi.exception.EntityType.SEAT;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class SeatServiceImpl implements SeatService {
    Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<SeatDto> getAllSeats() {
        return StreamSupport
                .stream(seatRepository.findAll().spliterator(), false)
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public SeatDto addSeat(SeatDto seatDto, UserDto userDto) {
        Seat seatModel = new Seat()
                .setName(seatDto.getName())
                .setNumber(seatDto.getNumber())
                .setRoomDetail(seatDto.getRoom())
                .setSeatTypeDetail(seatDto.getSeatType());

        seatModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        seatModel.setCreatedBy(userDto.getId());

        seatRepository.save(seatModel);
        return modelMapper.map(seatModel, SeatDto.class);
    }

    @Transactional
    public SeatDto updateSeat(SeatDto seatDto, UserDto userDto) {
        Optional<Seat> seat = seatRepository.findById(seatDto.getId());
        if (seat != null) {
            Seat seatModel = seat.get();
            seatModel.setName(seatDto.getName())
                    .setNumber(seatDto.getNumber())
                    .setRoomDetail(seatDto.getRoom())
                    .setSeatTypeDetail(seatDto.getSeatType());

            seatModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            seatModel.setModifiedBy(userDto.getId());

            return modelMapper.map(seatRepository.save(seatModel), SeatDto.class);
        }

        throw exceptionWithId(SEAT, ENTITY_NOT_FOUND, 2, seatDto.getId().toString());
    }

    @Transactional
    public void deleteSeat(Long seatID) {
        Optional<Seat> seat = seatRepository.findById(seatID);
        if (seat != null && seat.isPresent()) {
            Seat seatModel = seat.get();

            Set<BillDetailDto> billDetailDtos = billDetailService.getAllBillDetails(null, true);

            for (BillDetailDto billDetailDto: billDetailDtos) {
                if (billDetailDto.getSeat() != null && billDetailDto.getSeat().getId() == seatID) {
                    billDetailDto.setSeat(null);
                    billDetailService.updateBillDetail(billDetailDto);
                }
            }

            seatRepository.deleteById(seatID);
        } else {
            throw exceptionWithId(SEAT, ENTITY_NOT_FOUND, 2, seatID.toString());
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
