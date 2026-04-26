package com.example.chartered_accountant.security;

import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.AdminRepo;
import com.example.chartered_accountant.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private AdminRepo adminRepo;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @ParameterizedTest
    @CsvSource({
           "USER,ahmed@example.com,encodedPass,ROLE_USER",
            "ADMIN,admin1,adminPass,ROLE_ADMIN"
    })
    void loadUserByUsername_shouldReturnCorrectPrincipal(
            String type, String username, String password, String expectedRole) {

        UUID id = UUID.randomUUID();

        if (type.equals("USER")) {
            User user = new User();
            user.setId(id);
            user.setEmail(username);
            user.setPassword(password);
            when(userRepo.findByEmail(username)).thenReturn(Optional.of(user));
        } else {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setUsername(username);
            admin.setPassword(password);
            when(adminRepo.findByUsername(username)).thenReturn(Optional.of(admin));
        }

        UserDetails details = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, details.getUsername());
        assertEquals(password, details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(expectedRole)));
    }

    @ParameterizedTest
    @CsvSource({
            "USER,ahmed@example.com,encodedPass,ROLE_USER",
            "ADMIN,admin1,adminPass,ROLE_ADMIN"
    })
    void loadUserById_shouldReturnCorrectPrincipal(
            String type, String username, String password, String expectedRole) {

        UUID id = UUID.randomUUID();

        if (type.equals("USER")) {
            User user = new User();
            user.setId(id);
            user.setEmail(username);
            user.setPassword(password);
            when(userRepo.findById(id)).thenReturn(Optional.of(user));
        } else {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setUsername(username);
            admin.setPassword(password);
            when(adminRepo.findById(id)).thenReturn(Optional.of(admin));
        }

        UserDetails details = customUserDetailsService.loadUserById(id);

        assertEquals(username, details.getUsername());
        assertEquals(password, details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(expectedRole)));
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenNotFound() {
        when(userRepo.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        when(adminRepo.findByUsername("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("missing@example.com"));
    }

    @Test
    void loadUserById_shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        when(adminRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserById(id));
    }
}
