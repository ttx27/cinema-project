package com.example.cinemaapi.controller.v1.api.movie;

import com.example.cinemaapi.controller.v1.request.movie.ActorRequest;
import com.example.cinemaapi.dto.model.movie.ActorDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.movie.ActorService;
import com.example.cinemaapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllActors() {
        return Response
                .ok().setPayload(actorService.getAllActors());
    }

    @PostMapping("")
    public Response createActor(Principal principal, @RequestBody @Valid ActorRequest actorRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        ActorDto actorDto = new ActorDto()
                .setFullName(actorRequest.getFullName());

            return Response
                .ok().setPayload(actorService.addActor(actorDto, userDto));
    }

    @PutMapping("/{id}")
    public Response updateActor(Principal principal, @PathVariable Long id, @RequestBody @Valid ActorRequest actorRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        ActorDto actorDto = new ActorDto()
            .setId(id)
            .setFullName(actorRequest.getFullName());

        return Response
                .ok().setPayload(actorService.updateActor(actorDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return Response.ok();
    }
}
