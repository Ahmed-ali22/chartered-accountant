package com.example.chartered_accountant.repository;

import com.example.chartered_accountant.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepo extends JpaRepository<Appointment, UUID> {
}
