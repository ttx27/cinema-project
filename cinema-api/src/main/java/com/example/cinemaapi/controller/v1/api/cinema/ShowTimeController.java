package com.example.cinemaapi.controller.v1.api.cinema;

import com.example.cinemaapi.controller.v1.request.cinema.ShowTimeRequest;
import com.example.cinemaapi.controller.v1.request.movie.SubtitleRequest;
import com.example.cinemaapi.dto.model.cinema.SeatDto;
import com.example.cinemaapi.dto.model.cinema.ShowTimeDto;
import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.dto.response.ResponseError;
import com.example.cinemaapi.model.bill.Bill;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.cinema.*;
import com.example.cinemaapi.model.movie.Category;
import com.example.cinemaapi.repository.bill.BillDetailRepository;
import com.example.cinemaapi.repository.bill.BillRepository;
import com.example.cinemaapi.repository.cinema.MovieRoomRepository;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.repository.cinema.ShowTimeRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.cinema.MovieShiftService;
import com.example.cinemaapi.service.cinema.SeatService;
import com.example.cinemaapi.service.cinema.ShowTimeService;
import com.example.cinemaapi.service.movie.ActorServiceImpl;
import com.example.cinemaapi.service.movie.SubtitleService;
import com.example.cinemaapi.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/show-times")
public class ShowTimeController {
    Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    @Autowired
    private ShowTimeService showTimeService;

    @Autowired
    private MovieShiftService movieShiftService;

    @Autowired
    private MovieRoomRepository movieRoomRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Response getAllShowTimes() {
        return Response
                .ok().setPayload(showTimeService.getAllShowTimes());
    }

    @PostMapping("")
    public Response createShowTime(Principal principal, @RequestBody @Valid ShowTimeRequest showTimeRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        ShowTimeDto showTimeDto = new ShowTimeDto()
            .setMovieTime(showTimeRequest.getMovieTime())
            .setMovieDate(showTimeRequest.getMovieDate());

        if (showTimeRequest.getShifts() != null && !showTimeRequest.getShifts().isEmpty()) {
            List<MovieShift> shifts = movieShiftService.getMovieShiftByIds(showTimeRequest.getShifts());
            if (shifts != null && !shifts.isEmpty()) {
                showTimeDto.setShifts(shifts);
            }
        }

        if (showTimeRequest.getMovieRoom() != null && showTimeRequest.getMovieRoom() > 0) {
            Optional<MovieRoom> movieRoom = movieRoomRepository.findById(showTimeRequest.getMovieRoom());

            if (movieRoom != null && movieRoom.isPresent()) {
                showTimeDto.setMovieRoom(movieRoom.get());
            }
        }

        return Response
            .ok().setPayload(showTimeService.addShowTime(showTimeDto, userDto));
    }

    @GetMapping("/{id}/seats")
    public Response getSeatAvailbles(@PathVariable Long id) {
        Optional<ShowTime> showTime = showTimeRepository.findById(id);

        if (!showTime.isPresent()) {
            ResponseError error = new ResponseError()
                    .setMessage("Suất chiếu không tồn tại!")
                    .setTimestamp(DateUtils.today());
            return Response
                    .notFound().setErrors(error);
        }

        List<Bill> bills = billRepository.findAllByShowTimeDetailId(id);
        List<Long> billIds = new ArrayList<Long>();

        for (Bill bill: bills) {
            billIds.add(bill.getId());
        }

        List<BillDetail> billDetails = billDetailRepository.findByBillDetailIdIn(billIds);
        List<Long> billDetailSeatBookedIds = new ArrayList<Long>();

        for (BillDetail billDetail: billDetails) {
            if (billDetail.getSeatDetail() != null) {
                billDetailSeatBookedIds.add(billDetail.getSeatDetail().getId());
            }
        }

        ShowTime showTimeModel = showTime.get();
        Set<SeatDto> seats = seatService.getAllSeats();
        Set<SeatDto> showTimeSeats = new HashSet<SeatDto>();

        for (SeatDto seat: seats) {
            if (seat.getRoom().getId() == showTimeModel.getMovieRoomDetail().getRoomDetail().getId()) {
                if (!billDetailSeatBookedIds.contains(seat.getId())) {
                    showTimeSeats.add(seat);
                }
            }
        }

        return Response
                .ok().setPayload(showTimeSeats);
    }

    @PutMapping("/{id}")
    public Response updateShowTime(Principal principal, @PathVariable Long id, @RequestBody @Valid ShowTimeRequest showTimeRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        ShowTimeDto showTimeDto = new ShowTimeDto()
            .setId(id)
            .setMovieTime(showTimeRequest.getMovieTime())
            .setMovieDate(showTimeRequest.getMovieDate());

        if (showTimeRequest.getShifts() != null && !showTimeRequest.getShifts().isEmpty()) {
            List<MovieShift> shifts = movieShiftService.getMovieShiftByIds(showTimeRequest.getShifts());
            if (shifts != null && !shifts.isEmpty()) {
                showTimeDto.setShifts(shifts);
            }
        }

        if (showTimeRequest.getMovieRoom() != null && showTimeRequest.getMovieRoom() > 0) {
            Optional<MovieRoom> movieRoom = movieRoomRepository.findById(showTimeRequest.getMovieRoom());

            if (movieRoom != null && movieRoom.isPresent()) {
                showTimeDto.setMovieRoom(movieRoom.get());
            }
        }

        return Response
                .ok().setPayload(showTimeService.updateShowTime(showTimeDto, userDto));
    }

    @DeleteMapping("/{id}")
    public Response deleteShowTime(@PathVariable Long id) {
        showTimeService.deleteShowTime(id);
        return Response.ok();
    }
}
