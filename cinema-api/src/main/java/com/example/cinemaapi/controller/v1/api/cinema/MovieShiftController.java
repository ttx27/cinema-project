package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.MovieShiftRequest;
import com.example.cinemaapi.controller.v1.request.movie.DirectorRequest;
import com.example.cinemaapi.dto.model.cinema.MovieShiftDto;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.MovieShiftService;
import com.example.cinemaapi.service.movie.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/movie-shifts")
public class MovieShiftController {
    @Autowired
    private MovieShiftService movieShiftService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllMovieShifts() {
        return Response
                .ok().setPayload(movieShiftService.getAllMovieShifts());
    }

    @PostMapping("")
    public Response createMovieShift(Principal principal, @RequestBody @Valid MovieShiftRequest movieShiftRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieShiftDto movieShiftDto = new MovieShiftDto()
                .setTime(movieShiftRequest.getTime());

            return Response
                .ok().setPayload(movieShiftService.addMovieShift(movieShiftDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateMovieShift(Principal principal, @PathVariable Long id, @RequestBody @Valid MovieShiftRequest movieShiftRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieShiftDto movieShiftDto = new MovieShiftDto()
            .setId(id)
            .setTime(movieShiftRequest.getTime());

        return Response
                .ok().setPayload(movieShiftService.updateMovieShift(movieShiftDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteMovieShift(@PathVariable Long id) {
        movieShiftService.deleteMovieShift(id);
        return Response.ok();
    }
}
