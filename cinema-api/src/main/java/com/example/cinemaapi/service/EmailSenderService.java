package com.example.cinemaapi.service;

/**
 * .
 */
public interface EmailSenderService {
    void sendConfirmationEmail(String email, String confirmationCode);

    void sendSuccessResetPasswordEmail(String email, String confirmationCode);
}
