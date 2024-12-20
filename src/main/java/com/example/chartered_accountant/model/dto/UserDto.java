package com.example.chartered_accountant.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class UserDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2 , max = 30 , message = "Name is required and should be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not Valid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "\\+201[0-2,5]\\d{8}$", message = "Phone number must be valid according to Egypt's criteria")
    private String phone;

    private String companyName;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
