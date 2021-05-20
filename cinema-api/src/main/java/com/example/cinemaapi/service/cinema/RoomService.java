package com.example.cinemaapi.service.cinema;

import com.example.cinemaapi.dto.model.cinema.RoomDto;
import com.example.cinemaapi.dto.model.movie.LanguageDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.movie.Language;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface RoomService {
    Set<RoomDto> getAllRooms();

    RoomDto addRoom(RoomDto roomDto, UserDto userDto);

    RoomDto updateRoom(RoomDto roomDto, UserDto userDto);

    void deleteRoom(Long roomID);
}
