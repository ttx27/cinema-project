package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.DirectorRequest;
import com.example.cinemaapi.dto.model.movie.DirectorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.movie.DirectorService;
import com.example.cinemaapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllDirectors() {
        return Response
                .ok().setPayload(directorService.getAllDirectors());
    }

    @PostMapping("")
    public Response createDirector(Principal principal, @RequestBody @Valid DirectorRequest directorRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        DirectorDto directorDto = new DirectorDto()
                .setFullName(directorRequest.getFullName());

            return Response
                .ok().setPayload(directorService.addDirector(directorDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateDirector(Principal principal, @PathVariable Long id, @RequestBody @Valid DirectorRequest directorRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        DirectorDto directorDto = new DirectorDto()
            .setId(id)
            .setFullName(directorRequest.getFullName());

        return Response
                .ok().setPayload(directorService.updateDirector(directorDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return Response.ok();
    }
}
