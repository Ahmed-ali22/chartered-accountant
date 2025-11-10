package com.example.chartered_accountant.service.admin;

import com.example.chartered_accountant.error.exception.AdminException;
import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.Admin.AdminResponseDto;
import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.repository.AdminRepo;
import com.example.chartered_accountant.util.mapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public void save(AdminRequestDto adminRequestDto) {
        if(adminRepo.existsByUsername(adminRequestDto.getUsername())) {
            throw new AdminException(
                    409, "adminConflict", "Username already exists with Username : " + adminRequestDto.getUsername()
            );
        }
        Admin admin = AdminMapper.toEntity(adminRequestDto);
        adminRepo.save(admin);
        log.info("New Admin added successfully");
    }

    @Override
    public void updateById(UUID id , AdminRequestDto adminRequestDto) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(()-> new AdminException(
                404, "adminNotFound", "Admin not found with Username: "+ adminRequestDto.getUsername()
        ));
        Admin updatedAdmin = AdminMapper.updateToEntity(adminRequestDto, admin);
        adminRepo.save(updatedAdmin);
        log.info("The Admin is Successfully Updated");
    }

    @Override
    public void deleteById(UUID id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(()-> new AdminException(
                        404, "adminNotFound", "Admin not found with ID : "+ id
                ));
        adminRepo.delete(admin);
        log.info("Admin Successfully Deleted with ID : {}", id);
    }

    @Override
    public List<AdminResponseDto> findAll() {
        List<Admin> adminsList = adminRepo.findAll();
        if(adminsList.isEmpty()){
            throw new AdminException(
                    404, "noAdminsFound", "No Admins exist in the system"
            );
        }
        return adminsList.stream().map(AdminMapper::toDto).toList();
    }

    @Override
    public AdminResponseDto findById(UUID id) {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(()-> new AdminException(
                        404, "adminNotFound", "Admin not found with ID: "+ id
                ));
        return AdminMapper.toDto(admin);
    }


}
