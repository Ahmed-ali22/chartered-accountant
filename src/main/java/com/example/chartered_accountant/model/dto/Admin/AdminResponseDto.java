package com.example.chartered_accountant.model.dto.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
public class AdminResponseDto {
    @NotNull
    private UUID id;
    @NotBlank
    private String username;
}

