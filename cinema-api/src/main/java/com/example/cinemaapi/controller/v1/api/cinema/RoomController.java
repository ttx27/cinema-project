package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.RoomRequest;
import com.example.cinemaapi.controller.v1.request.movie.LanguageRequest;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.movie.Actor;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.CinemaRepository;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.RoomService;
import com.example.cinemaapi.service.movie.LanguageService;
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
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllRooms() {
        return Response
                .ok().setPayload(roomService.getAllRooms());
    }

    @PostMapping("")
    public Response createRoom(Principal principal, @RequestBody @Valid RoomRequest roomRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        RoomDto roomDto = new RoomDto()
            .setName(roomRequest.getName())
            .setDescription(roomRequest.getDescription())
            .setStatus(1);

        if (roomRequest.getCinema() != null && roomRequest.getCinema() > 0) {
            Optional<Cinema> cinema = cinemaRepository.findById(roomRequest.getCinema());

            if (cinema != null && cinema.isPresent()) {
                roomDto.setCinema(cinema.get());
            }
        }

        return Response
            .ok().setPayload(roomService.addRoom(roomDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateRoom(Principal principal, @PathVariable Long id, @RequestBody @Valid RoomRequest roomRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        RoomDto roomDto = new RoomDto()
            .setId(id)
            .setName(roomRequest.getName())
            .setDescription(roomRequest.getDescription())
            .setStatus(1);

        if (roomRequest.getCinema() != null && roomRequest.getCinema() > 0) {
            Optional<Cinema> cinema = cinemaRepository.findById(roomRequest.getCinema());

            if (cinema != null && cinema.isPresent()) {
                roomDto.setCinema(cinema.get());
            }
        }

        return Response
                .ok().setPayload(roomService.updateRoom(roomDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return Response
                .ok();
    }
}
