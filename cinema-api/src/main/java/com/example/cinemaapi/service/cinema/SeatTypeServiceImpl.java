package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.cinema.SeatTypeDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.SeatType;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.movie.Subtitle;
import com.example.cinemaapi.repository.cinema.SeatTypeRepository;
import com.example.cinemaapi.repository.movie.SubtitleRepository;
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
import static com.example.cinemaapi.exception.EntityType.SUBTITLE;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class SeatTypeServiceImpl implements SeatTypeService {
    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<SeatTypeDto> getAllSeatTypes() {
        return StreamSupport
                .stream(seatTypeRepository.findAll().spliterator(), false)
                .map(seatType -> modelMapper.map(seatType, SeatTypeDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public SeatTypeDto addSeatType(SeatTypeDto seatTypeDto, UserDto userDto) {
        SeatType seatTypeModel = new SeatType().setName(seatTypeDto.getName())
                .setPrice(seatTypeDto.getPrice());

        seatTypeModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        seatTypeModel.setCreatedBy(userDto.getId());

        seatTypeRepository.save(seatTypeModel);
        return modelMapper.map(seatTypeModel, SeatTypeDto.class);
    }

    @Transactional
    public SeatTypeDto updateSeatType(SeatTypeDto seatTypeDto, UserDto userDto) {
        Optional<SeatType> seatType = seatTypeRepository.findById(seatTypeDto.getId());
        if (seatType != null) {
            SeatType seatTypeModel = seatType.get();
            seatTypeModel.setName(seatTypeDto.getName())
                    .setPrice(seatTypeDto.getPrice());

            seatTypeModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            seatTypeModel.setModifiedBy(userDto.getId());

            return modelMapper.map(seatTypeRepository.save(seatTypeModel), SeatTypeDto.class);
        }
        throw exceptionWithId(SEATTYPE, ENTITY_NOT_FOUND, 2, seatTypeDto.getId().toString());
    }

    @Transactional
    public void deleteSeatType(Long seatTypeID) {
        Optional<SeatType> seatType = seatTypeRepository.findById(seatTypeID);
        if (seatType != null && seatType.isPresent()) {
            SeatType seatTypeModel = seatType.get();

            Set<SeatDto> seatDtos = seatService.getAllSeats();

            for (SeatDto seatDto: seatDtos) {
                if (seatDto.getSeatType() != null && seatDto.getSeatType().getId() == seatTypeID) {
                    seatService.deleteSeat(seatDto.getId());
                }
            }

            seatTypeRepository.deleteById(seatTypeID);
        } else {
            throw exceptionWithId(SEATTYPE, ENTITY_NOT_FOUND, 2, seatTypeID.toString());
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
