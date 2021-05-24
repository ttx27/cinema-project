package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.CinemaRequest;
import com.example.cinemaapi.controller.v1.request.movie.ActorRequest;
import com.example.cinemaapi.dto.mapper.UserMapper;
import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.CinemaService;
import com.example.cinemaapi.service.movie.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/cinemas")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllCinemas() {
        return Response
                .ok().setPayload(cinemaService.getAllCinemas());
    }

    @PostMapping("")
    public Response createCinema(Principal principal, @RequestBody @Valid CinemaRequest cinemaRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        CinemaDto cinemaDto = new CinemaDto()
            .setName(cinemaRequest.getName())
            .setDescription(cinemaRequest.getDescription())
            .setCity(cinemaRequest.getCity())
            .setAddress(cinemaRequest.getAddress())
            .setPhone(cinemaRequest.getPhone())
            .setCityId(cinemaRequest.getCityId())
            .setStatus(1);

        return Response
            .ok().setPayload(cinemaService.addCinema(cinemaDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateCinema(Principal principal, @PathVariable Long id, @RequestBody @Valid CinemaRequest cinemaRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        CinemaDto cinemaDto = new CinemaDto()
            .setId(id)
            .setName(cinemaRequest.getName())
            .setDescription(cinemaRequest.getDescription())
            .setCity(cinemaRequest.getCity())
            .setAddress(cinemaRequest.getAddress())
            .setPhone(cinemaRequest.getPhone())
            .setCityId(cinemaRequest.getCityId())
            .setStatus(1);

        return Response
                .ok().setPayload(cinemaService.updateCinema(cinemaDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return Response.ok();
    }
}
