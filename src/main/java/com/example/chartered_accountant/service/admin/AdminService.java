package com.example.chartered_accountant.service.admin;

import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.Admin.AdminResponseDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void save(AdminRequestDto adminRequestDto);
    void updateById(UUID id , AdminRequestDto adminRequestDto);
    void deleteById(UUID id);
    List<AdminResponseDto> findAll();
    AdminResponseDto findById(UUID id);

}
