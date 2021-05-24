package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.repository.cinema.CinemaRepository;
import com.example.cinemaapi.repository.movie.ActorRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.ACTOR;
import static com.example.cinemaapi.exception.EntityType.CINEMA;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class CinemaServiceImpl implements CinemaService {
    Logger logger = LoggerFactory.getLogger(CinemaServiceImpl.class);

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<CinemaDto> getAllCinemas() {
        return StreamSupport
            .stream(cinemaRepository.findAll().spliterator(), false)
            .map(cinema -> modelMapper.map(cinema, CinemaDto.class))
            .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public CinemaDto addCinema(CinemaDto cinemaDto, UserDto userDto) {
        Cinema cinemaModel = new Cinema().setName(cinemaDto.getName())
            .setAddress(cinemaDto.getAddress())
            .setCity(cinemaDto.getCity())
            .setCityId(cinemaDto.getCityId())
            .setDescription(cinemaDto.getDescription())
            .setStatus(cinemaDto.getStatus())
            .setPhone(cinemaDto.getPhone());

        cinemaModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        cinemaModel.setCreatedBy(userDto.getId());

        cinemaRepository.save(cinemaModel);
        return modelMapper.map(cinemaModel, CinemaDto.class);
    }

    @Transactional
    public CinemaDto updateCinema(CinemaDto cinemaDto, UserDto userDto) {
        Optional<Cinema> cinema = cinemaRepository.findById(cinemaDto.getId());
        if (cinema != null) {
            Cinema cinemaModel = cinema.get();
            cinemaModel.setName(cinemaDto.getName())
                .setAddress(cinemaDto.getAddress())
                .setCity(cinemaDto.getCity())
                .setCityId(cinemaDto.getCityId())
                .setDescription(cinemaDto.getDescription())
                .setStatus(cinemaDto.getStatus())
                .setPhone(cinemaDto.getPhone());

            cinemaModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            cinemaModel.setModifiedBy(userDto.getId());

            return modelMapper.map(cinemaRepository.save(cinemaModel), CinemaDto.class);
        }
        throw exceptionWithId(CINEMA, ENTITY_NOT_FOUND, 2, cinemaDto.getId().toString());
    }

    @Transactional
    public void deleteCinema(Long cinemaID) {
        Optional<Cinema> cinema = cinemaRepository.findById(cinemaID);
        if (cinema != null) {
            Set<RoomDto> roomDtos = roomService.getAllRooms();

            for (RoomDto roomDto: roomDtos) {
                if (roomDto.getCinema() != null && roomDto.getCinema().getId() == cinemaID) {
                    roomService.deleteRoom(roomDto.getId());
                }
            }

            cinemaRepository.deleteById(cinemaID);
        } else {
            throw exceptionWithId(CINEMA, ENTITY_NOT_FOUND, 2, cinemaID.toString());
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
