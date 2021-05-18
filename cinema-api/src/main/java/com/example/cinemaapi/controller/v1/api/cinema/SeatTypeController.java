package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.SeatRequest;
import com.example.cinemaapi.controller.v1.request.cinema.SeatTypeRequest;
import com.example.cinemaapi.controller.v1.request.movie.SubtitleRequest;
import com.example.cinemaapi.dto.model.cinema.SeatTypeDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.cinema.SeatType;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.SeatTypeService;
import com.example.cinemaapi.service.movie.SubtitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/seat-types")
public class SeatTypeController {
    @Autowired
    private SeatTypeService seatTypeService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllSeatTypes() {
        return Response
                .ok().setPayload(seatTypeService.getAllSeatTypes());
    }

    @PostMapping("")
    public Response createSeatType(Principal principal, @RequestBody @Valid SeatTypeRequest seatTypeRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SeatTypeDto seatTypeDto = new SeatTypeDto()
            .setName(seatTypeRequest.getName())
            .setPrice(seatTypeRequest.getPrice());

        return Response
            .ok().setPayload(seatTypeService.addSeatType(seatTypeDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateSeatType(Principal principal, @PathVariable Long id, @RequestBody @Valid SeatTypeRequest seatTypeRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        SeatTypeDto seatTypeDto = new SeatTypeDto()
            .setId(id)
            .setName(seatTypeRequest.getName())
            .setPrice(seatTypeRequest.getPrice());

        return Response
                .ok().setPayload(seatTypeService.updateSeatType(seatTypeDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteSeatType(@PathVariable Long id) {
        seatTypeService.deleteSeatType(id);
        return Response.ok();
    }
}
