package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Appointment save (UUID userId, AppointmentRequestDto appointmentRequestDto);

    Appointment update(UUID appointmentId,AppointmentRequestDto appointmentUpdateDto);

    List<Appointment> findByUserId(UUID userId);

    List<Appointment> findAll();

    void deleteById(UUID appointmentId);
}
