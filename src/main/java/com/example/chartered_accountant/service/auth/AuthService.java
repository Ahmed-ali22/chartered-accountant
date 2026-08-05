package com.example.chartered_accountant.service.auth;

import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;
import com.example.chartered_accountant.model.dto.Auth.RefreshTokenRequestDto;

import java.util.UUID;

public interface AuthService {

    AuthResponseDto login(AuthRequestDto authRequestDto);
    AuthResponseDto refreshSession(RefreshTokenRequestDto requestDto);
    void logout(UUID ownerId);
}
