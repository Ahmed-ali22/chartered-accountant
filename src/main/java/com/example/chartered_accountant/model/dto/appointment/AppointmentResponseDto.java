package com.example.chartered_accountant.model.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class AppointmentResponseDto {
    @NotNull
    @NotNull(message = "user Email is required")
    private String userEmail;

    @NotNull(message = "date and time are required")
    private LocalDateTime dateTime;

    @NotNull(message = "status is required")
    @Pattern(regexp = "scheduled|completed|cancelled", message = "Status must be one of scheduled, completed, or cancelled")
    private String status;

    @NotBlank(message = "Type of service is required")
    private String service;
}
