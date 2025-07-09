package com.example.chartered_accountant.repository;

import com.example.chartered_accountant.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepo extends JpaRepository<Admin, UUID> {
    boolean existsByUsername(String username);
}
