package com.example.chartered_accountant.service.auth;

import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;
import com.example.chartered_accountant.security.CustomUserPrincipal;
import com.example.chartered_accountant.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_shouldReturnToken_whenAuthenticationSucceeds() {
        AuthRequestDto requestDto = new AuthRequestDto("ahmed@example.com", "Password123@");
        CustomUserPrincipal principal = new CustomUserPrincipal(
                UUID.randomUUID(),
                "ahmed@example.com",
                "Password123@",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(principal)).thenReturn("jwt-token");

        AuthResponseDto response = authService.login(requestDto);

        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void login_shouldPropagateException_whenAuthenticationFails() {
        AuthRequestDto requestDto = new AuthRequestDto("wrong@example.com", "BadPass");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        assertThrows(RuntimeException.class, () -> authService.login(requestDto));
    }
}
