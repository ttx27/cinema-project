package com.example.cinemaapi.controller.v1.api.bill;

import com.example.cinemaapi.controller.v1.request.bill.BillRequest;
import com.example.cinemaapi.controller.v1.request.cinema.CinemaRequest;
import com.example.cinemaapi.dto.model.bill.BillDetailDto;
import com.example.cinemaapi.dto.model.bill.BillDto;
import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.dto.model.cinema.CinemaDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.dto.response.ResponseError;
import com.example.cinemaapi.model.bill.Bill;
import com.example.cinemaapi.model.bill.BillDetail;
import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.cinema.Room;
import com.example.cinemaapi.model.cinema.Seat;
import com.example.cinemaapi.model.cinema.ShowTime;
import com.example.cinemaapi.model.movie.Movie;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.model.user.UserRoles;
import com.example.cinemaapi.repository.bill.BillDetailRepository;
import com.example.cinemaapi.repository.bill.BillRepository;
import com.example.cinemaapi.repository.bill.PaymentRepository;
import com.example.cinemaapi.repository.cinema.RoomRepository;
import com.example.cinemaapi.repository.cinema.SeatRepository;
import com.example.cinemaapi.repository.cinema.ShowTimeRepository;
import com.example.cinemaapi.repository.movie.MovieRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.bill.BillDetailService;
import com.example.cinemaapi.service.bill.BillService;
import com.example.cinemaapi.service.cinema.CinemaService;
import com.example.cinemaapi.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.cinemaapi.exception.EntityType.BILL;
import static com.example.cinemaapi.exception.ExceptionType.ENTITY_NOT_FOUND;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("")
    public Response getAllBills(Principal principal) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        return Response
                .ok().setPayload(billService.getAllBills(userDto.getId(), userDto.isAdmin()));
    }

    @PostMapping("")
    public Response createBill(Principal principal, @RequestBody @Valid BillRequest billRequest) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.findUserByEmail(currentPrincipalName);

        List<Bill> bills = billRepository.findAllByShowTimeDetailId(billRequest.getShowTime());
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

        for (Long seatId: billRequest.getSeats()) {
            if (billDetailSeatBookedIds.contains(seatId)) {
                ResponseError error = new ResponseError()
                        .setDetails("Ghế đã có người đặt, vui lòng chọn ghế khác!")
                        .setMessage("Ghế đã có người đặt, vui lòng chọn ghế khác!")
                        .setTimestamp(DateUtils.today());
                return Response.duplicateEntity().setErrors(error);
            }
        }


        BillDto billDto = new BillDto()
            .setTotal(billRequest.getTotal())
            .setCode("B"+System.currentTimeMillis()+billRequest.getCustomer()+billRequest.getShowTime())
            .setStatus(0);

        if (billRequest.getCustomer() != null && billRequest.getCustomer() > 0) {
            Optional<User> user = userRepository.findById(billRequest.getCustomer());

            if (user != null && user.isPresent()) {
                billDto.setCustomer(user.get());
            }
        }

        if (billRequest.getShowTime() != null && billRequest.getShowTime() > 0) {
            Optional<ShowTime> showTime = showTimeRepository.findById(billRequest.getShowTime());

            if (showTime != null && showTime.isPresent()) {
                billDto.setShowTime(showTime.get());
            }
        }

        BillDto billCreated = billService.addBill(billDto, userDto);
        Optional<Bill> bill = billRepository.findById(billCreated.getId());

        if (bill != null && bill.isPresent()) {
            Bill billModel = bill.get();

            if (billRequest.getSeats() != null && billRequest.getSeats().size() > 0) {
                for (Long seatID: billRequest.getSeats()) {
                    Optional<Seat> seat = seatRepository.findById(seatID);

                    if (seat != null && seat.isPresent()) {
                        BillDetailDto billDetailDto = new BillDetailDto().setBill(billModel)
                                .setSeat(seat.get())
                                .setTicketNumber("B"+System.currentTimeMillis()+billModel.getId()+seat.get().getId())
                                .setStatus(1);
                        billDetailService.addBillDetail(billDetailDto, userDto);
                    }
                }
            }
        }

        return Response
            .ok().setPayload(bill.get());
    }

    @GetMapping("/{code}/bycode")
    public Response getBillByCode(Principal principal, @PathVariable String code) {
        String currentPrincipalName = principal.getName();
        User user = userRepository.findByEmail(currentPrincipalName);
        List<UserRoles> roleList = new ArrayList<>();

        for (Role role: user.getRoles()) {
            roleList.add(role.getCode());
        }

        if (!roleList.contains(UserRoles.STAFF)) {
            ResponseError error = new ResponseError()
                    .setMessage("Bạn không có quyền truy cập dữ liệu này!")
                    .setTimestamp(DateUtils.today());
            return Response.unauthorized().setErrors(error);
        }

        Bill bill = billRepository.findByCode(code);

        if (bill == null) {
            ResponseError error = new ResponseError()
                .setMessage("Hóa đơn không tồn tại!")
                .setTimestamp(DateUtils.today());
            return Response.notFound().setErrors(error);
        }

        BillDto billDto = new ModelMapper().map(bill, BillDto.class);

        List<Long> billIds = new ArrayList<Long>();
        billIds.add(billDto.getId());

        List<BillDetail> billDetails = billDetailRepository.findByBillDetailIdIn(billIds);

        billDto.setBillDetails(billDetails.stream().map(billDetail -> new ModelMapper().map(billDetail, BillDetailDto.class)).collect(Collectors.toSet()));

        return Response
                .ok().setPayload(billDto);
    }

    @PostMapping("/{id}/checkout")
    public Response checkout(Principal principal, @PathVariable Long id) {
        String currentPrincipalName = principal.getName();
        User user = userRepository.findByEmail(currentPrincipalName);

        Optional<Bill> bill = billRepository.findById(id);

        if (!bill.isPresent()) {
            return Response.notFound();
        }

        Bill billModel = bill.get();

        Integer totalPayment = billModel.getTotal() + 10*billModel.getTotal()/100;

        if (totalPayment > user.getPayment().getAmount() || billModel.getStatus() != 0) {
            return  Response.badRequest();
        }

        Payment payment = user.getPayment();
        payment.setAmount(payment.getAmount() - totalPayment);
        payment.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        payment.setModifiedBy(user.getId());

        paymentRepository.save(payment);

        billModel.setStatus(1);
        billModel.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
        billModel.setModifiedBy(user.getId());
        billRepository.save((billModel));

        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return Response.ok();
    }
}
