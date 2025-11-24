package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.service.CustomUserDetailsService;
import com.example.chartered_accountant.service.appointment.AppointmentService;
import com.example.chartered_accountant.util.mapper.AppointmentMapper;
import com.example.chartered_accountant.util.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/me")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@AuthenticationPrincipal CustomUserPrincipal principal
            , @Valid  @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentService.save(principal.getUserId(), appointmentRequestDto);
        return ResponseEntity.ok(AppointmentMapper.toAppointmentResponseDto(appointment));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable UUID appointmentId
            ,@AuthenticationPrincipal CustomUserPrincipal principal,@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Appointment updatedAppointment = appointmentService.updateForUser(appointmentId,principal.getUserId(), appointmentRequestDto);
        return ResponseEntity.ok(AppointmentMapper.toAppointmentResponseDto(updatedAppointment));
    }

    @GetMapping("/me")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointments(@AuthenticationPrincipal CustomUserPrincipal principal) {
        List<Appointment> appointments = appointmentService.findByUserId(principal.getUserId());
        return ResponseEntity.ok(appointments.stream().map(AppointmentMapper::toAppointmentResponseDto).toList());
    }

}
