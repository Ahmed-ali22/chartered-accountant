package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.user.UserDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import com.example.chartered_accountant.model.entity.User;

import java.util.Optional;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .companyName(Optional.ofNullable(userDto.getCompanyName()).orElse(""))
                .description(Optional.ofNullable(userDto.getDescription()).orElse(""))
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .companyName(Optional.ofNullable(user.getCompanyName()).orElse(""))
                .description(Optional.ofNullable(user.getDescription()).orElse(""))
                .build();
    }

    public static User updateEntityFromDto(UserUpdateDto userDto , User entity) {
        entity.setName(userDto.getName());
        entity.setPassword(userDto.getPassword());
        entity.setPhoneNumber(userDto.getPhoneNumber());
        entity.setCompanyName(userDto.getCompanyName());
        entity.setDescription(userDto.getDescription());
        return entity;
    }

}
