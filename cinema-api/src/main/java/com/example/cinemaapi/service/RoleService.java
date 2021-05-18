package com.example.cinemaapi.service;

import com.example.cinemaapi.dto.model.movie.SubtitleDto;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.model.movie.Subtitle;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface RoleService {
    Set<RoleDto> getAllRoles();
}
