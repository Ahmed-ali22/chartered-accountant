package com.example.chartered_accountant.repository;

import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepo extends JpaRepository<Appointment, UUID> {

 boolean existsByDateTime(LocalDateTime dateTime);
 List<Appointment> findByUserId(UUID userId);
}
