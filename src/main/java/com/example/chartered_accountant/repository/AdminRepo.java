package com.example.chartered_accountant.repository;

import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepo extends JpaRepository<Admin, UUID> {

    boolean existsByUsername(String username);
    Optional<Admin> findByUsername(String username);
}
