package com.example.chartered_accountant.service.admin;

import com.example.chartered_accountant.error.exception.AdminException;
import com.example.chartered_accountant.model.dto.Admin.AdminRequestDto;
import com.example.chartered_accountant.model.dto.Admin.AdminResponseDto;
import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.repository.AdminRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    @Mock
    AdminRepo adminRepo;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    AdminServiceImpl adminService;

    @Test
    void save_shouldSaveAdmin_whenUsernameNotExist() {
        AdminRequestDto adminRequestDto = new AdminRequestDto();
        adminRequestDto.setUsername("admin123");
        adminRequestDto.setPassword("AdminPass123@");
        when(adminRepo.existsByUsername(adminRequestDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(adminRequestDto.getPassword())).thenReturn("encodedPass");

        adminService.save(adminRequestDto);

        verify(adminRepo, times(1)).save(any(Admin.class));
        verify(passwordEncoder, times(1)).encode("AdminPass123@");
    }

    @Test
    void save_shouldThrow409_whenUsernameAlreadyExists() {
        AdminRequestDto adminRequestDto = new AdminRequestDto();
        adminRequestDto.setUsername("admin123");
        adminRequestDto.setPassword("AdminPass123@");

        when(adminRepo.existsByUsername(adminRequestDto.getUsername())).thenReturn(true);

        AdminException ex = assertThrows(AdminException.class, () -> adminService.save(adminRequestDto));

        assertEquals(409, ex.getErrorCode());
        verify(adminRepo, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }
    @Test
    void updateById_shouldUpdateAdmin_whenAdminExists() {
        UUID id = UUID.randomUUID();
        Admin existing = new Admin();
        existing.setId(id);
        existing.setUsername("admin1");
        existing.setPassword("encodedPass");

        AdminRequestDto dto = new AdminRequestDto();
        dto.setUsername("adminUpdated");
        dto.setPassword("NewPass123@");

        when(adminRepo.findById(id)).thenReturn(Optional.of(existing));

        adminService.updateById(id, dto);

        verify(adminRepo, times(1)).save(any(Admin.class));
    }

    @Test
    void updateById_shouldThrow404_whenAdminNotFound() {
        UUID id = UUID.randomUUID();
        AdminRequestDto dto = new AdminRequestDto();
        dto.setUsername("adminUpdated");
        dto.setPassword("NewPass123@");

        when(adminRepo.findById(id)).thenReturn(Optional.empty());

        AdminException ex = assertThrows(AdminException.class, () -> adminService.updateById(id, dto));

        assertEquals(404, ex.getErrorCode());
        verify(adminRepo, never()).save(any());
    }
    @Test
    void deleteById_shouldDeleteAdmin_whenAdminExists() {
        UUID id = UUID.randomUUID();
        Admin existing = new Admin();
        existing.setId(id);
        existing.setUsername("admin1");
        existing.setPassword("encodedPass");

        when(adminRepo.findById(id)).thenReturn(Optional.of(existing));

        adminService.deleteById(id);

        verify(adminRepo, times(1)).delete(existing);
    }

    @Test
    void deleteById_shouldThrow404_whenAdminNotFound() {
        UUID id = UUID.randomUUID();

        when(adminRepo.findById(id)).thenReturn(Optional.empty());

        AdminException ex = assertThrows(AdminException.class, () -> adminService.deleteById(id));

        assertEquals(404, ex.getErrorCode());
        verify(adminRepo, never()).delete(any());
    }
    @Test
    void findAll_shouldReturnAdmins_whenAdminsExist() {
        Admin admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setUsername("admin1");
        admin.setPassword("encodedPass");

        when(adminRepo.findAll()).thenReturn(List.of(admin));

        List<AdminResponseDto> result = adminService.findAll();

        assertFalse(result.isEmpty());
        assertEquals("admin1", result.get(0).getUsername());
    }

    @Test
    void findAll_shouldThrow404_whenNoAdminsExist() {
        when(adminRepo.findAll()).thenReturn(List.of());

        AdminException ex = assertThrows(AdminException.class, () -> adminService.findAll());

        assertEquals(404, ex.getErrorCode());
    }
    @Test
    void findById_shouldReturnAdmin_whenAdminExists() {
        UUID id = UUID.randomUUID();
        Admin admin = new Admin();
        admin.setId(id);
        admin.setUsername("admin1");
        admin.setPassword("encodedPass");

        when(adminRepo.findById(id)).thenReturn(Optional.of(admin));

        AdminResponseDto result = adminService.findById(id);

        assertEquals("admin1", result.getUsername());
    }

    @Test
    void findById_shouldThrow404_whenAdminNotFound() {
        UUID id = UUID.randomUUID();

        when(adminRepo.findById(id)).thenReturn(Optional.empty());

        AdminException ex = assertThrows(AdminException.class, () -> adminService.findById(id));

        assertEquals(404, ex.getErrorCode());
    }

}
