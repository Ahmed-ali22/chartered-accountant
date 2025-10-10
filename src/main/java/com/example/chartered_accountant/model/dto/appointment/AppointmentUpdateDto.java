package com.example.chartered_accountant.model.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentUpdateDto {
    private LocalDateTime dateTime;

    @Pattern(regexp = "scheduled|completed|cancelled", message = "Status must be one of scheduled, completed, or cancelled")
    private String status;

    private String service;
}
