package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.AppointmentDto;
import com.example.chartered_accountant.model.entity.Appointment;

public class AppointmentMapper {

    private AppointmentMapper() {}

    public static Appointment toEntity(AppointmentDto appointmentDto) {
        return Appointment.builder()
                .user(UserMapper.toEntity(appointmentDto.getUserDto()))
                .dateTime(appointmentDto.getDateTime())
                .status(appointmentDto.getStatus())
                .service(appointmentDto.getService())
                .build();
    }

    public static AppointmentDto toDto(Appointment appointment) {
        return AppointmentDto.builder()
                .userDto(UserMapper.toDto(appointment.getUser()))
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .build();
    }
}
