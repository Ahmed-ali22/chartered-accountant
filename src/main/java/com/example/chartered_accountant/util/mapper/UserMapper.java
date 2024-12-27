package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.UserDto;
import com.example.chartered_accountant.model.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .companyName(userDto.getCompanyName())
                .description(userDto.getDescription())
                .build();
    }

    public static UserDto userDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .companyName(user.getCompanyName())
                .description(user.getDescription())
                .build();
    }
}
