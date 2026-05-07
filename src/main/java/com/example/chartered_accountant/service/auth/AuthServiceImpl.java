package com.example.chartered_accountant.service.auth;

import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;
import com.example.chartered_accountant.security.CustomUserPrincipal;
import com.example.chartered_accountant.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authManager;
    private final Jwt jwt;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authManager, Jwt jwt) {
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
        );
        CustomUserPrincipal userDetails = (CustomUserPrincipal) auth.getPrincipal();
        String token = jwt.generateToken(userDetails);
        return new AuthResponseDto(token);
    }
}
