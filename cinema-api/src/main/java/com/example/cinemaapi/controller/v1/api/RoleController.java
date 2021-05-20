package com.example.cinemaapi.controller.v1.api;

import com.example.cinemaapi.controller.v1.request.UserRequest;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.repository.user.RoleRepository;
import com.example.cinemaapi.service.EmailSenderService;
import com.example.cinemaapi.service.RoleService;
import com.example.cinemaapi.service.UserService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/roles")
@Api(value = "cinema-application", description = "Operations pertaining to user management in the cinema application")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public Response getAllRoles() {
        return Response
                .ok().setPayload(roleService.getAllRoles());
    }
}
