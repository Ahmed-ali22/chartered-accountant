package com.example.chartered_accountant.model.dto;

import com.example.chartered_accountant.util.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class AppointmentDto {
    @NotNull(message = "user data is required")
    private UserDto userDto;

    @NotNull(message = "date and time are required")
    private LocalDateTime dateTime;

    @NotNull(message = "status is required")
    @Pattern(regexp = "SCHEDULED|COMPLETED|CANCELLED", message = "Status must be one of SCHEDULED, COMPLETED, or CANCELLED")
    private StatusEnum status;

    @NotBlank(message = "Type of service is required")
    private String service;
}
