package com.example.cinemaapi.service;

import com.example.cinemaapi.dto.model.movie.MovieDto;
import com.example.cinemaapi.dto.model.user.UserDto;

import java.util.Set;

/**
 * .
 */
public interface UserService {
    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto verify(String verifyCode);

    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto signup(UserDto userDto, UserDto userCreateDto);

    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto signin(String email, String password);

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    UserDto findUserByEmail(String email);

    UserDto getUserDetailByEmail(String email);

    /**
     * Update profile of the user
     *
     * @param userDto
     * @return
     */
    UserDto updateProfile(UserDto userDto);

    /**
     * Update password
     *
     * @param newPassword
     * @return
     */
    UserDto changePassword(UserDto userDto, String newPassword);

    Set<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto, UserDto userModifyDto);

    void deleteUser(Long userID);
}
