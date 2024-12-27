package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.AdminDto;
import com.example.chartered_accountant.model.entity.Admin;

public class AdminMapper {

    private AdminMapper() {}

    public static Admin toEntity (AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        return admin;
    }

    public static AdminDto toDto (Admin admin) {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername(admin.getUsername());
        adminDto.setPassword(admin.getPassword());
        return adminDto;
    }
}
