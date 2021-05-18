package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.MovieRoomRequest;
import com.example.cinemaapi.controller.v1.request.movie.CategoryRequest;
import com.example.cinemaapi.dto.model.cinema.MovieRoomDto;
import com.example.cinemaapi.dto.model.movie.CategoryDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.MovieRoomService;
import com.example.cinemaapi.service.cinema.RoomService;
import com.example.cinemaapi.service.movie.CategoryService;
import com.example.cinemaapi.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * .
 */
@RestController
    @RequestMapping("/api/v1/movie-rooms")
public class MovieRoomController {
    @Autowired
    private MovieRoomService movieRoomService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllMovieRooms() {
        return Response
                .ok().setPayload(movieRoomService.getAllMovieRooms());
    }

    @PostMapping("")
    public Response createMovieRoom(Principal principal, @RequestBody @Valid MovieRoomRequest movieRoomRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieRoomDto movieRoomDto = new MovieRoomDto();

        if (movieRoomRequest.getMovie() != null && movieRoomRequest.getMovie() > 0) {
            Optional<Movie> movie = movieRepository.findById(movieRoomRequest.getMovie());

            if (movie != null && movie.isPresent()) {
                movieRoomDto.setMovie(movie.get());
            }
        }

        if (movieRoomRequest.getRoom() != null && movieRoomRequest.getRoom() > 0) {
            Optional<Room> room = roomRepository.findById(movieRoomRequest.getRoom());

            if (room != null && room.isPresent()) {
                movieRoomDto.setRoom(room.get());
            }
        }

        return Response
            .ok().setPayload(movieRoomService.addMovieRoom(movieRoomDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateMovieRoom(Principal principal, @PathVariable Long id, @RequestBody @Valid MovieRoomRequest movieRoomRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        MovieRoomDto movieRoomDto = new MovieRoomDto()
            .setId(id);

        if (movieRoomRequest.getMovie() != null && movieRoomRequest.getMovie() > 0) {
            Optional<Movie> movie = movieRepository.findById(movieRoomRequest.getMovie());

            if (movie != null && movie.isPresent()) {
                movieRoomDto.setMovie(movie.get());
            }
        }

        if (movieRoomRequest.getRoom() != null && movieRoomRequest.getRoom() > 0) {
            Optional<Room> room = roomRepository.findById(movieRoomRequest.getRoom());

            if (room != null && room.isPresent()) {
                movieRoomDto.setRoom(room.get());
            }
        }

        return Response
                .ok().setPayload(movieRoomService.updateMovieRoom(movieRoomDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteMovieRoom(@PathVariable Long id) {
        movieRoomService.deleteMovieRoom(id);
        return Response.ok();
    }
}
