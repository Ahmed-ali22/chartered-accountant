package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.model.dto.appointment.AppointmentDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;
import com.example.chartered_accountant.model.entity.Appointment;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    void save (AppointmentDto appointmentDto);

    void update(AppointmentUpdateDto appointmentUpdateDto , UUID id);

    List<Appointment> findByUserEmail(String userEmail);

    List<Appointment> findAll();
}
