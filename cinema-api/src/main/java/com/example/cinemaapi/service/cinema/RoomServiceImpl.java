package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.cinema.MovieRoomDto;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.cinema.MovieRoom;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.movie.Language;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.repository.movie.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.cinemaapi.exception.EntityType.LANGUAGE;
import static com.example.cinemaapi.exception.EntityType.ROOM;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@Component
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private MovieRoomService movieRoomService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<RoomDto> getAllRooms() {
        return StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public RoomDto addRoom(RoomDto roomDto, UserDto userDto) {
        Room roomModel = new Room().setName(roomDto.getName())
                .setStatus(roomDto.getStatus())
                .setDescription(roomDto.getDescription())
                .setCinemaDetail(roomDto.getCinema());

        roomModel.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        roomModel.setCreatedBy(userDto.getId());

        roomRepository.save(roomModel);
        return modelMapper.map(roomModel, RoomDto.class);
    }

    @Transactional
    public RoomDto updateRoom(RoomDto roomDto, UserDto userDto) {
        Optional<Room> room = roomRepository.findById(roomDto.getId());
        if (room != null) {
            Room roomModel = room.get();
            roomModel.setName(roomDto.getName())
                    .setStatus(roomDto.getStatus())
                    .setDescription(roomDto.getDescription())
                    .setCinemaDetail(roomDto.getCinema());

            roomModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            roomModel.setModifiedBy(userDto.getId());

            return modelMapper.map(roomRepository.save(roomModel), RoomDto.class);
        }
        throw exceptionWithId(ROOM, ENTITY_NOT_FOUND, 2, roomDto.getId().toString());
    }

    @Transactional
    public void deleteRoom(Long roomID) {
        Optional<Room> room = roomRepository.findById(roomID);
        if (room != null && room.isPresent()) {
            Room roomModel = room.get();

            Set<SeatDto> seatDtos = seatService.getAllSeats();

            for (SeatDto seatDto: seatDtos) {
                if (seatDto.getRoom() != null && seatDto.getRoom().getId() == roomID) {
                    seatService.deleteSeat(seatDto.getId());
                }
            }

            Set<MovieRoomDto> movieRoomDtos = movieRoomService.getAllMovieRooms();

            for (MovieRoomDto movieRoomDto: movieRoomDtos) {
                if (movieRoomDto.getRoom() != null && movieRoomDto.getRoom().getId() == roomID) {
                    movieRoomService.deleteMovieRoom(movieRoomDto.getId());
                }
            }

            roomRepository.deleteById(roomID);
        } else {
            throw exceptionWithId(ROOM, ENTITY_NOT_FOUND, 2, roomID.toString());
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
