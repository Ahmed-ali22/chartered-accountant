package com.example.chartered_accountant.service.auth;

import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;

public interface AuthService {

    AuthResponseDto login(AuthRequestDto authRequestDto);
}
