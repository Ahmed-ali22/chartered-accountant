package com.example.chartered_accountant.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDto {
    @NotBlank(message = "username is required")
    @NotNull
    private String username;

    @NotBlank(message = "password is required")
    @NotNull
    private String password;
}
