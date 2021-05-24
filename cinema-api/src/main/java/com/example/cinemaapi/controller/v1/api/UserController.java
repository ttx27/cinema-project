package com.example.cinemaapi.controller.v1.api;

import com.example.cinemaapi.controller.v1.api.movie.MovieController;
import com.example.cinemaapi.controller.v1.request.*;
import com.example.cinemaapi.controller.v1.request.movie.MovieRequest;
import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.movie.*;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.repository.user.RoleRepository;
import com.example.cinemaapi.service.EmailSenderService;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.util.RandomStringUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/users")
@Api(value = "cinema-application", description = "Operations pertaining to user management in the cinema application")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("")
    public Response getAllUsers() {
        return Response
                .ok().setPayload(userService.getAllUsers());
    }

    @PostMapping("")
    public Response createUser(Principal principal, @Valid @RequestBody UserRequest userRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userCreateDto = userService.findUserByEmail(currentPrincipalName);

        Set<RoleDto> roleDtos = null;

        if (userRequest.getRoles() != null) {
            roleDtos = new HashSet<RoleDto>();
            Iterable<Role> roles = roleRepository.findAllById(userRequest.getRoles());

            for (Role role: roles) {
                roleDtos.add(new ModelMapper().map(role, RoleDto.class));
            }
        }

        UserDto userDto = new UserDto()
                .setEmail(userRequest.getEmail())
                .setPassword(userRequest.getPassword())
                .setFullName(userRequest.getFullName())
                .setStatus(userRequest.getStatus())
                .setRoles(roleDtos);

        userService.signup(userDto, userCreateDto);

        return Response.ok().setPayload(userDto);
    }

    @PutMapping("/{id}")
    public Response updateUser(Principal principal, @PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userModifyDto = userService.findUserByEmail(currentPrincipalName);

        Set<RoleDto> roleDtos = null;

        if (userRequest.getRoles() != null) {
            roleDtos = new HashSet<RoleDto>();
            Iterable<Role> roles = roleRepository.findAllById(userRequest.getRoles());

            for (Role role: roles) {
                roleDtos.add(new ModelMapper().map(role, RoleDto.class));
            }
        }

        UserDto userDto = new UserDto()
                .setId(id)
                .setEmail(userRequest.getEmail())
                .setPassword(userRequest.getPassword() != null ? userRequest.getPassword() : null)
                .setFullName(userRequest.getFullName())
                .setStatus(userRequest.getStatus())
                .setRoles(roleDtos);

        return Response.ok().setPayload(userService.updateUser(userDto, userModifyDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Response.ok();
    }
}
