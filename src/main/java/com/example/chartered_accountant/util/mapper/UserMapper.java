package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.user.AdminUserResponseDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.dto.user.UserResponseDto;
import com.example.chartered_accountant.model.entity.User;

import java.util.Optional;

public class UserMapper {

    private UserMapper() {}

    public static User toUserEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .companyName(Optional.ofNullable(userRequestDto.getCompanyName()).orElse(""))
                .description(Optional.ofNullable(userRequestDto.getDescription()).orElse(""))
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .companyName(Optional.ofNullable(user.getCompanyName()).orElse(""))
                .description(Optional.ofNullable(user.getDescription()).orElse(""))
                .build();
    }

    public static AdminUserResponseDto toAdminUserResponseDto(User user) {
        return AdminUserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .companyName(Optional.ofNullable(user.getCompanyName()).orElse(""))
                .description(Optional.ofNullable(user.getDescription()).orElse(""))
                .build();
    }


    public static User updateEntityFromDto(UserRequestDto userRequestDto, User user) {
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setCompanyName(userRequestDto.getCompanyName());
        user.setDescription(userRequestDto.getDescription());
        return user;
    }

}
