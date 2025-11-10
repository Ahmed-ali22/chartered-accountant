package com.example.chartered_accountant.util.mapper;

import com.example.chartered_accountant.model.dto.appointment.AdminAppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;

public class AppointmentMapper {

    private AppointmentMapper() {}

    public static Appointment toAppointmentEntity(AppointmentRequestDto appointmentRequestDto, User user) {
        return Appointment.builder()
                .user(user)
                .dateTime(appointmentRequestDto.getDateTime())
                .status(appointmentRequestDto.getStatus())
                .service(appointmentRequestDto.getService())
                .build();
    }
    public static AppointmentResponseDto toAppointmentResponseDto (Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    public static AdminAppointmentResponseDto toAdminAppointmentResponseDto (Appointment appointment) {
        return AdminAppointmentResponseDto.builder()
                .id(appointment.getId())
                .userId(appointment.getUser().getId())
                .userName(appointment.getUser().getName())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .service(appointment.getService())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();

    }


    public static Appointment updateToEntity(AppointmentRequestDto appointmentRequestDto , Appointment appointment ) {
        appointment.setDateTime(appointmentRequestDto.getDateTime());
        appointment.setStatus(appointmentRequestDto.getStatus());
        appointment.setService(appointmentRequestDto.getService());
        return appointment;
    }


}
