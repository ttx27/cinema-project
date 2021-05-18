package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.SeatRequest;
import com.example.cinemaapi.controller.v1.request.movie.MovieRequest;
import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.cinema.SeatType;
import com.example.cinemaapi.model.movie.*;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.repository.cinema.SeatTypeRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.SeatService;
import com.example.cinemaapi.service.movie.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {
    Logger logger = LoggerFactory.getLogger(SeatController.class);

    @Autowired
    private SeatService seatService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllSeats() {
        return Response
                .ok().setPayload(seatService.getAllSeats());
    }

    @PostMapping("")
    public Response createSeat(Principal principal, @RequestBody @Valid SeatRequest seatRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SeatDto seatDto = new SeatDto()
            .setName(seatRequest.getName())
            .setNumber(seatRequest.getNumber());

        if (seatRequest.getSeatType() != null && seatRequest.getSeatType() > 0) {
            Optional<SeatType> seatType = seatTypeRepository.findById(seatRequest.getSeatType());

            if (seatType != null && seatType.isPresent()) {
                seatDto.setSeatType(seatType.get());
            }
        }

        if (seatRequest.getRoom() != null && seatRequest.getRoom() > 0) {
            Optional<Room> room = roomRepository.findById(seatRequest.getRoom());

            if (room != null && room.isPresent()) {
                seatDto.setRoom(room.get());
            }
        }

        return Response
            .ok().setPayload(seatService.addSeat(seatDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateSeat(Principal principal, @PathVariable Long id, @RequestBody @Valid SeatRequest seatRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SeatDto seatDto = new SeatDto()
            .setId(id)
                .setName(seatRequest.getName())
                .setNumber(seatRequest.getNumber());

        if (seatRequest.getSeatType() != null && seatRequest.getSeatType() > 0) {
            Optional<SeatType> seatType = seatTypeRepository.findById(seatRequest.getSeatType());
            logger.info("saddsasdadsasd " + seatType);
            if (seatType != null && seatType.isPresent()) {
                seatDto.setSeatType(seatType.get());
            }
        }

        if (seatRequest.getRoom() != null && seatRequest.getRoom() > 0) {
            Optional<Room> room = roomRepository.findById(seatRequest.getRoom());

            logger.info("saddsasdadsasd " + room);
            if (room != null && room.isPresent()) {
                seatDto.setRoom(room.get());
            }
        }

        return Response
                .ok().setPayload(seatService.updateSeat(seatDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return Response
                .ok();
    }
}
