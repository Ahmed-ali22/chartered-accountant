package com.example.chartered_accountant.service.Admin;

import com.example.chartered_accountant.model.dto.AdminDto;
import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.repository.AdminRepo;
import com.example.chartered_accountant.util.mapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServiceImpl  implements AdminService{

    private final AdminRepo adminRepo;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo){
        this.adminRepo = adminRepo;
        log.info(" Admin Repository injected successfully ");
    }

    @Override
    public void save(AdminDto adminDto) {
        if(adminRepo.existsByUsername(adminDto.getUsername())) {
            throw new IllegalArgumentException(" Username already in use");
        }
        Admin admin = AdminMapper.toEntity(adminDto);
        adminRepo.save(admin);
        log.info("New Admin added successfully");
    }
}
