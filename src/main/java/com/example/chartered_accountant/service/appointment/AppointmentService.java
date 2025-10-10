package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentSaveDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    void save (AppointmentSaveDto appointmentSaveDto);

    AppointmentResponseDto update(AppointmentUpdateDto appointmentUpdateDto , UUID id);

    List<AppointmentSaveDto> findByUserEmail(String userEmail);

    List<AppointmentSaveDto> findAll();
}
