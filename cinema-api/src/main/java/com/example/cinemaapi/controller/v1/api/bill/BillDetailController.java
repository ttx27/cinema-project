package com.example.cinemaapi.controller.v1.api.bill;

import com.example.cinemaapi.controller.v1.request.cinema.RoomRequest;
import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.Cinema;
import com.example.cinemaapi.repository.cinema.CinemaRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.bill.BillDetailService;
import com.example.cinemaapi.service.cinema.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/bill-details")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllBillDetails(Principal principal) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        return Response
                .ok().setPayload(billDetailService.getAllBillDetails(userDto.getId(), userDto.isAdmin()));
    }
}
