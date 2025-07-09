package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.appointment.AppointmentDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;

public class AppointmentMapper {

    private AppointmentMapper() {}

    public static Appointment toEntity(AppointmentDto appointmentDto , User user) {
        return Appointment.builder()
                .user(user)
                .dateTime(appointmentDto.getDateTime())
                .status(appointmentDto.getStatus())
                .service(appointmentDto.getService())
                .build();
    }

    public static AppointmentDto toDto(Appointment appointment) {
        return AppointmentDto.builder()
                .userEmail(appointment.getUser().getEmail())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .build();
    }
}
