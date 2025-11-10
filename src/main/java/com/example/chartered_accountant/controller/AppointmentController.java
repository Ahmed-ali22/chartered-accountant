package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.service.appointment.AppointmentService;
import com.example.chartered_accountant.util.mapper.AppointmentMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        log.info("Appointment Service Injected");
    }
    @PostMapping("/{userId}")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@PathVariable UUID userId
            ,@Valid  @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentService.save(userId, appointmentRequestDto);
        return ResponseEntity.ok(AppointmentMapper.toAppointmentResponseDto(appointment));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable UUID appointmentId
            ,@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Appointment updatedAppointment = appointmentService.update(appointmentId, appointmentRequestDto);
        return ResponseEntity.ok(AppointmentMapper.toAppointmentResponseDto(updatedAppointment));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointments(@PathVariable UUID userId) {
        List<Appointment> appointments = appointmentService.findByUserId(userId);
        return ResponseEntity.ok(appointments.stream().map(AppointmentMapper::toAppointmentResponseDto).toList());
    }

}
