package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentSaveDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;

public class AppointmentMapper {

    private AppointmentMapper() {}

    public static Appointment saveToEntity(AppointmentSaveDto appointmentSaveDto, User user) {
        return Appointment.builder()
                .user(user)
                .dateTime(appointmentSaveDto.getDateTime())
                .status(appointmentSaveDto.getStatus())
                .service(appointmentSaveDto.getService())
                .build();
    }

    public static AppointmentSaveDto saveToDto(Appointment appointment) {
        return AppointmentSaveDto.builder()
                .userEmail(appointment.getUser().getEmail())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .build();
    }

    public static Appointment updateToEntity(AppointmentUpdateDto appointmentUpdateDto , Appointment appointment ) {
        appointment.setDateTime(appointmentUpdateDto.getDateTime());
        appointment.setStatus(appointmentUpdateDto.getStatus());
        appointment.setService(appointmentUpdateDto.getService());
        return appointment;
    }

    public static AppointmentResponseDto responseToDto (Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .build();
    }
}
