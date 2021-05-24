package com.example.cinemaapi.service;

import com.example.cinemaapi.dto.mapper.UserMapper;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.exception.BRSException;
import com.example.cinemaapi.exception.EntityType;
import com.example.cinemaapi.exception.ExceptionType;
import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.model.user.UserRoles;
import com.example.cinemaapi.repository.user.RoleRepository;
import com.example.cinemaapi.repository.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

import static com.example.cinemaapi.exception.EntityType.USER;
import static com.example.cinemaapi.exception.ExceptionType.*;
import static com.example.cinemaapi.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.cinemaapi.security.SecurityConstants.SECRET;

/**
 * .
 */
@Component
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

//    private Environment env;
//    String frontendOrigin = env.getProperty("config.frontendOrigin");

    @Override
    public void sendConfirmationEmail(String email, String confirmationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no.reply.7xit@gmail.com");
        message.setTo(email);
        message.setSubject("Xác thực email đăng ký tài khoản");
        message.setText(String.format("Click vào link để xác thực tài khoản: \n%s/verify?code=%s\n", "http://localhost:8081", confirmationCode));
        emailSender.send(message);
    }

    @Override
    public void sendSuccessResetPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no.reply.7xit@gmail.com");
        message.setTo(email);
        message.setSubject("Bạn vừa yêu cầu đổi mật khẩu");
        message.setText(String.format("Yêu cầu đổi mật khẩu của bạn đã được xử lý thành công. Mật khẩu mới của bạn là: \n%s\n\nVui lòng đăng nhập và đổi mật khẩu ngay!", "http://localhost:8081", newPassword));
        emailSender.send(message);
    }
}
