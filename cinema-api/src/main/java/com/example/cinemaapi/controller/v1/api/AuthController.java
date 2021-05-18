package com.example.cinemaapi.controller.v1.api;

import com.example.cinemaapi.controller.v1.request.UserResetPasswordRequest;
import com.example.cinemaapi.controller.v1.request.UserSigninRequest;
import com.example.cinemaapi.controller.v1.request.UserSignupRequest;
import com.example.cinemaapi.controller.v1.request.UserVerifyRequest;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.dto.response.Response;
import com.example.cinemaapi.service.EmailSenderService;
import com.example.cinemaapi.service.UserService;
import com.example.cinemaapi.util.RandomStringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Random;

/**
 * .
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "cinema-application", description = "Operations pertaining to user management in the cinema application")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/signup")
    public Response signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        return Response.ok().setPayload(registerUser(userSignupRequest, false));
    }

    private UserDto registerUser(UserSignupRequest userSignupRequest, boolean isAdmin) {
        String verifyCode = RandomStringUtil.getAlphaNumericString(7, "abcdefghijklmnopqrsutxyzABCDEGHIJKLMNOPQRSUTXYZ");

        UserDto userDto = new UserDto()
                .setEmail(userSignupRequest.getEmail())
                .setPassword(userSignupRequest.getPassword())
                .setFullName(userSignupRequest.getFullName())
                .setStatus(0)
                .setVerifyCode(verifyCode)
                .setAdmin(isAdmin);

        userService.signup(userDto, null);

        emailSenderService.sendConfirmationEmail(userDto.getEmail(), verifyCode);

        return userDto;
    }

    @GetMapping("/me")
    public Response profile(Principal principal) {
        String currentPrincipalName = principal.getName();
        UserDto userDto = userService.getUserDetailByEmail(currentPrincipalName);
        userDto.setPassword("");
        return Response.ok().setPayload(userDto);
    }

    @PostMapping("/signin")
    public Response signin(@Valid @RequestBody UserSigninRequest userSigninRequest) {
        return Response.ok().setPayload(signinUser(userSigninRequest));
    }

    private UserDto signinUser(UserSigninRequest userSigninRequest) {
        return userService.signin(userSigninRequest.getEmail(), userSigninRequest.getPassword());
    }

    @PostMapping("/verify")
    public Response verify(@Valid @RequestBody UserVerifyRequest userVerifyRequest) {
        return Response.ok().setPayload(verifyUser(userVerifyRequest));
    }

    private UserDto verifyUser(UserVerifyRequest userVerifyRequest) {
        return userService.verify(userVerifyRequest.getCode());
    }

    @PostMapping("/reset-password")
    public Response resetPassword(@Valid @RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        UserDto user = userService.findUserByEmail(userResetPasswordRequest.getEmail());

        String newPassword = RandomStringUtil.getAlphaNumericString(10, "!@#$%^&*()abcdefghijklmnopqrsutxyzwvABCDEGHIJKLMNOPQRSUTXYZWV");

        return Response.ok().setPayload(changePasswordUser(user, newPassword));
    }

    private UserDto changePasswordUser(UserDto userDto, String newPassword) {
        UserDto _userDto = userService.changePassword(userDto, newPassword);

        emailSenderService.sendSuccessResetPasswordEmail(userDto.getEmail(), newPassword);

        return _userDto;
    }
}
