package com.example.chartered_accountant.model.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class AppointmentResponseDto {
    @NotNull
    private UUID id;

    @NotNull(message = "date and time are required")
    private LocalDateTime dateTime;

    @NotNull(message = "status is required")
    @Pattern(regexp = "scheduled|completed|cancelled", message = "Status must be one of scheduled, completed, or cancelled")
    private String status;

    @NotBlank(message = "Type of service is required")
    private String service;
}
