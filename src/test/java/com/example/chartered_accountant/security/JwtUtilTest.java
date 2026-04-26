package com.example.chartered_accountant.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {
    private JwtUtil jwtUtil;
    private CustomUserPrincipal principal;

    @BeforeEach
    void setUp() {
        String secret = "mysecretkeymysecretkeymysecretkeymysecretkey";
        jwtUtil = new JwtUtil(secret, 1000L * 60); //

        principal = new CustomUserPrincipal(
                UUID.randomUUID(),
                "ahmed@example.com",
                "Password123@",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
