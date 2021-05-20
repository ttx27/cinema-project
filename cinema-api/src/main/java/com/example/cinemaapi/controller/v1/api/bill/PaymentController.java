package com.example.cinemaapi.controller.v1.api.bill;

import com.example.cinemaapi.controller.v1.api.cinema.SeatController;
import com.example.cinemaapi.controller.v1.request.bill.PaymentRequest;
import com.example.cinemaapi.controller.v1.request.bill.PaymentVNPayRequest;
import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.repository.bill.PaymentRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.service.bill.BillDetailService;
import com.example.cinemaapi.service.bill.PaymentService;
import com.example.cinemaapi.util.VNPayConfig;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @GetMapping("/vnpay-return")
    public void getPaymentUrl(@RequestParam String vnp_Amount, @RequestParam String vnp_ResponseCode, @RequestParam String vnp_TxnRef, HttpServletResponse resp) {
        Optional<User> user = userRepository.findById(Long.parseLong(vnp_TxnRef.split("_")[1]));

        if (!user.isPresent()) {
            resp.sendRedirect("http://localhost:8081/payment?success=0");
            return;
        }

        User userModel = user.get();
        Payment payment = userModel.getPayment();

        Integer amount = Integer.parseInt(vnp_Amount)/100;

        if (payment == null) {
            Payment newPayment = new Payment().setAmount(amount).setTotalAmount(amount);
            newPayment.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            newPayment.setCreatedBy(userModel.getId());

            payment = paymentRepository.save(newPayment);
            userModel.setPayment(payment);
            userRepository.save(userModel);
        } else  {
            payment.setAmount(payment.getAmount() + amount).setTotalAmount((payment.getTotalAmount() + amount));
            payment.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            payment.setModifiedBy(userModel.getId());
            paymentRepository.save(payment);
        }

        resp.sendRedirect("http://localhost:8081/profile");
        return;
    }

    @PostMapping("/vnpay")
    public Response getPaymentUrl(@RequestBody @Valid PaymentVNPayRequest paymentVNPayRequest, HttpServletRequest request, Principal principal) {
        String currentPrincipalName = principal.getName();
        User user = userRepository.findByEmail(currentPrincipalName);

        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        String vnp_Version = "2.0.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = paymentVNPayRequest.getOrderInfo();
        String orderType = paymentVNPayRequest.getOrderType();
        String vnp_TxnRef = formatter.format(dt) + "_" + user.getId().toString();
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_TransactionNo = formatter.format(dt) + "_" + vnp_TxnRef;
        String vnp_hashSecret = VNPayConfig.vnp_HashSecret;
        int amount = Integer.parseInt(paymentVNPayRequest.getAmount())*100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = paymentVNPayRequest.getBankCode();
        if (bank_code != null && bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = paymentVNPayRequest.getLanguage();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        String dateString = formatter.format(dt);
        String vnp_CreateDate = dateString;
        String vnp_TransDate = vnp_CreateDate;
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                //Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                query.append('=');
                try {
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.Sha256(VNPayConfig.vnp_HashSecret + hashData.toString());
        //System.out.println("HashData=" + hashData.toString());
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return Response
                .ok().setPayload(paymentUrl);
    }

    @PostMapping("")
    public Response addPayment(@RequestBody @Valid PaymentRequest paymentRequest, Principal principal) {
        String currentPrincipalName = principal.getName();
        User user = userRepository.findByEmail(currentPrincipalName);

        Payment payment = user.getPayment();

        if (payment == null) {
            Payment newPayment = new Payment().setAmount(paymentRequest.getAmount()).setTotalAmount(paymentRequest.getAmount());
            newPayment.setCreatedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            newPayment.setCreatedBy(user.getId());

            payment = paymentRepository.save(newPayment);
            user.setPayment(payment);
            user.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            user.setModifiedBy(user.getId());
            userRepository.save(user);
        } else  {
            payment.setAmount(payment.getAmount()+ paymentRequest.getAmount()).setTotalAmount((payment.getTotalAmount()+ paymentRequest.getAmount()));
            payment.setModifiedDate(Instant.now().atZone(ZoneOffset.systemDefault()).toInstant());
            payment.setModifiedBy(user.getId());
            paymentRepository.save(payment);
        }

        return Response
            .ok().setPayload(new ModelMapper().map(payment, PaymentDto.class));
    }
}
