package com.example.chartered_accountant.model.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminRequestDto {
    @NotBlank(message = "username is required")
    @NotNull
    private String username;

    @NotBlank(message = "password is required")
    @NotNull
    private String password;
}
