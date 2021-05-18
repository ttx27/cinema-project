package com.example.cinemaapi.dto.mapper;

//import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.dto.model.bill.PaymentDto;
import com.example.cinemaapi.model.bill.Payment;
import com.example.cinemaapi.model.user.User;
import com.example.cinemaapi.dto.model.user.RoleDto;
import com.example.cinemaapi.dto.model.user.UserDto;
import com.example.cinemaapi.model.user.UserRoles;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * .
 */
@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        Payment payment = user.getPayment();
        PaymentDto paymentDto = new PaymentDto();

        if (payment != null) {
            paymentDto = new ModelMapper().map(user.getPayment(), PaymentDto.class);
        }

        Set<RoleDto> roleDtos = new HashSet<RoleDto>(user
                .getRoles()
                .stream()
                .map(role -> new ModelMapper().map(role, RoleDto.class))
                .collect(Collectors.toSet()));

        Boolean isAdmin = false;
        for (RoleDto roleDto: roleDtos) {
            if (roleDto.getCode() == UserRoles.ADMIN.toString()) {
                isAdmin = true;
            }
        }

        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setFullName(user.getFullName())
                .setStatus(user.getStatus())
                .setRecoveryCode(user.getRecoveryCode())
                .setPayment(paymentDto)
                .setAdmin(isAdmin)
                .setRoles(roleDtos);
    }

}
