package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentSaveDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;
import com.example.chartered_accountant.service.appointment.AppointmentService;
import com.example.chartered_accountant.util.mapper.AppointmentMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        log.info("Appointment Service Injected");
    }
    @PostMapping("save")
    public ResponseEntity<String> createAppointment(@Valid  @RequestBody AppointmentSaveDto appointmentSaveDto) {
        appointmentService.save(appointmentSaveDto);
        return ResponseEntity.ok("New Appointment Created");
    }

    @PutMapping("updateById/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable UUID id , @RequestBody AppointmentUpdateDto appointmentUpdateDto) {
        return ResponseEntity.ok(appointmentService.update(appointmentUpdateDto,id));
    }
}
