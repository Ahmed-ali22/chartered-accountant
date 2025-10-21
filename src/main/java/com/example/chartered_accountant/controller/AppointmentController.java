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

import java.util.List;
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
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid  @RequestBody AppointmentSaveDto appointmentSaveDto) {
        return ResponseEntity.ok(appointmentService.save(appointmentSaveDto));
    }

    @PutMapping("updateById/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable UUID id ,@Valid @RequestBody AppointmentUpdateDto appointmentUpdateDto) {
        return ResponseEntity.ok(appointmentService.update(appointmentUpdateDto,id));
    }

    @GetMapping("findByUserEmail/{userEmail}")
    public ResponseEntity<List<AppointmentResponseDto>> findByUserEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(appointmentService.findByUserEmail(userEmail));
    }
    @GetMapping("findAll")
    public ResponseEntity<List<AppointmentResponseDto>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }
}
