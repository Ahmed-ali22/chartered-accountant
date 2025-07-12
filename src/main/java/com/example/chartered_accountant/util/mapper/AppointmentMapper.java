package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.appointment.AppointmentDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;
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

    public static Appointment updateEntityFromDto(AppointmentUpdateDto appointmentUpdateDto , Appointment appointment ) {
        appointment.setDateTime(appointmentUpdateDto.getDateTime());
        appointment.setStatus(appointmentUpdateDto.getStatus());
        appointment.setService(appointmentUpdateDto.getService());
        return appointment;
    }
}
