package com.example.chartered_accountant.model.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AdminUserResponseDto {
    @NotNull
    private UUID id;

    @Size(min = 2 , max = 30 , message = "Name is required and should be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not Valid")
    private String email;

    @Pattern(regexp = "\\+201[0-2,5]\\d{8}$", message = "Phone number must be valid according to Egypt's criteria")
    private String phoneNumber;

    private String companyName;
    private String description;
}
