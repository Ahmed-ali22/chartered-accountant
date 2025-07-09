package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.model.dto.appointment.AppointmentDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;

public interface AppointmentService {
    void save (AppointmentDto appointmentDto);
    void update(AppointmentUpdateDto appointmentUpdateDto);
}
