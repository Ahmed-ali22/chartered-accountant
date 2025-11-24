package com.example.chartered_accountant.controller;

import com.example.chartered_accountant.model.dto.appointment.AdminAppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.dto.user.AdminUserResponseDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.service.appointment.AppointmentService;
import com.example.chartered_accountant.service.user.UserService;
import com.example.chartered_accountant.util.mapper.AppointmentMapper;
import com.example.chartered_accountant.util.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admins")
public class AdminController {
    private final UserService userService;
    private final AppointmentService appointmentService;

    @Autowired
    public AdminController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }


    // -------------------- USERS MANAGEMENT --------------------
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponseDto>> getAllUsers(){
        List<User> users = userService.findAll();
        List<AdminUserResponseDto> usersDto = users.stream().map(UserMapper::toAdminUserResponseDto).toList();
        return ResponseEntity.ok(usersDto);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<AdminUserResponseDto> getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        AdminUserResponseDto userDto = UserMapper.toAdminUserResponseDto(user);
        return ResponseEntity.ok(userDto);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<AdminUserResponseDto> updateUser(@PathVariable UUID id , @Valid @RequestBody UserRequestDto userRequestDto) {
        User user =  userService.update(id,userRequestDto);
        AdminUserResponseDto userDto = UserMapper.toAdminUserResponseDto(user);
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return  ResponseEntity.ok("User Deleted Successfully");
    }

    // -------------------- APPOINTMENTS MANAGEMENT --------------------
    @GetMapping("/appointments")
    public ResponseEntity<List<AdminAppointmentResponseDto>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        List<AdminAppointmentResponseDto> appointmentsDto = appointments
                .stream().map(AppointmentMapper::toAdminAppointmentResponseDto).toList();
        return ResponseEntity.ok(appointmentsDto);
    }
    @GetMapping("/appointments/{userId}")
    public ResponseEntity<List<AdminAppointmentResponseDto>> getAppointments(@PathVariable UUID userId) {
        List<Appointment> appointments = appointmentService.findByUserId(userId);
        List<AdminAppointmentResponseDto> appointmentsDto = appointments
                .stream().map(AppointmentMapper::toAdminAppointmentResponseDto).toList();
        return ResponseEntity.ok(appointmentsDto);
    }
    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AdminAppointmentResponseDto> updateAppointment(@PathVariable UUID appointmentId
            , @Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        Appointment updatedAppointment = appointmentService.updateForAdmin(appointmentId, appointmentRequestDto);
        AdminAppointmentResponseDto appointmentDto = AppointmentMapper.toAdminAppointmentResponseDto(updatedAppointment);
        return ResponseEntity.ok(appointmentDto);
    }
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable UUID appointmentId) {
        appointmentService.deleteById(appointmentId);
        return  ResponseEntity.ok("Appointment Deleted Successfully");
    }
}
