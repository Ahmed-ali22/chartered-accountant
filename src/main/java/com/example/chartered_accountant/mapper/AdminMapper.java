package com.example.chartered_accountant.mapper;

import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.Admin.AdminResponseDto;
import com.example.chartered_accountant.model.entity.Admin;

public class AdminMapper {

    private AdminMapper() {}

    public static Admin toEntity (AdminRequestDto adminRequestDto) {
        Admin admin = new Admin();
        admin.setUsername(adminRequestDto.getUsername());
        admin.setPassword(adminRequestDto.getPassword());
        return admin;
    }

    public static AdminResponseDto toDto (Admin admin) {
        AdminResponseDto dto = new AdminResponseDto();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        return dto;
    }

    public static Admin updateToEntity(AdminRequestDto adminRequestDto, Admin admin) {
        admin.setUsername(adminRequestDto.getUsername());
        admin.setPassword(adminRequestDto.getPassword());
        return admin;
    }
}
