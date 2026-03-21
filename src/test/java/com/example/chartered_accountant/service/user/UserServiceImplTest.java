package com.example.chartered_accountant.service.user;

import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.user.PasswordUpdateDto;
import com.example.chartered_accountant.model.dto.user.UserRequestDto;
import com.example.chartered_accountant.model.dto.user.UserUpdateDto;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.UserRepo;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_shouldSaveUser_whenEmailNotExist() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("Password123@")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();
        when(userRepo.existsByEmail(userRequestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPass");

        userService.save(userRequestDto);
        verify(userRepo,times(1)).save(any(User.class));
        verify(passwordEncoder,times(1)).encode("Password123@");
    }

    @Test
    void save_shouldThrow409_whenEmailAlreadyExists() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("Password123@")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();
        when(userRepo.existsByEmail(userRequestDto.getEmail())).thenReturn(true);

        UserException ex = assertThrows(UserException.class , () -> userService.save(userRequestDto));

        assertEquals(409 , ex.getErrorCode());
        verify(userRepo,never()).save(any());
        verify(passwordEncoder,never()).encode(any());
    }

    @Test
    void update_shouldUpdateUser_whenValid() {
        UUID id = UUID.randomUUID();
        UserUpdateDto dto = UserUpdateDto.builder()
                .name("Ahmed Updated")
                .email("newemail@example.com")
                .phoneNumber("+201012345678")
                .companyName("UpdatedCompany")
                .description("Updated description")
                .build();
        User existingUser = User.builder()
                .id(id)
                .name("Ahmed")
                .email("old@example.com")
                .password("hashedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Old description")
                .appointments(List.of())
                .build();
         when(userRepo.existsByEmailAndIdNot(dto.getEmail(),id)).thenReturn(false);
         when(userRepo.findById(id)).thenReturn(Optional.of(existingUser));

         userService.update(id,dto);
         verify(userRepo , times(1)).save(any(User.class));
    }

    @Test
    void update_shouldThrow409_whenEmailConflict() {
        UUID id = UUID.randomUUID();
        UserUpdateDto dto = UserUpdateDto.builder()
                .name("Ahmed Updated")
                .email("conflict@example.com")
                .phoneNumber("+201012345678")
                .companyName("UpdatedCompany")
                .description("Updated description")
                .build();

        when(userRepo.existsByEmailAndIdNot(dto.getEmail(), id)).thenReturn(true);

        UserException ex = assertThrows(UserException.class, () -> userService.update(id, dto));
        assertEquals(409, ex.getErrorCode());
    }

    @Test
    void deleteById_shouldDeleteUser_whenExists() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Entity for testing")
                .appointments(List.of())
                .build();

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        userService.deleteById(id);

        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void deleteById_shouldThrow404_whenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        UserException ex = assertThrows(UserException.class, () -> userService.deleteById(id));
        assertEquals(404, ex.getErrorCode());
    }

    @Test
    void deleteAll_shouldDeleteAllUsers() {
        userService.deleteAll();
        verify(userRepo, times(1)).deleteAll();
    }

    @Test
    void findById_shouldReturnUser_whenExists() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Entity for testing")
                .appointments(List.of())
                .build();

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);

        assertEquals("Ahmed", result.getName());
    }

    @Test
    void findById_shouldThrow404_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        UserException ex = assertThrows(UserException.class, () -> userService.findById(id));
        assertEquals(404, ex.getErrorCode());
    }

    @Test
    void findAll_shouldReturnUsers_whenNotEmpty() {
        List<User> users = List.of(User.builder()
                .id(UUID.randomUUID())
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Entity for testing")
                .appointments(List.of())
                .build());

        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void findAll_shouldThrow404_whenEmpty() {
        when(userRepo.findAll()).thenReturn(List.of());

        UserException ex = assertThrows(UserException.class, () -> userService.findAll());
        assertEquals(404, ex.getErrorCode());
    }

    // ---------------- UPDATE PASSWORD ----------------
    @Test
    void updatePassword_shouldUpdate_whenOldPasswordMatches() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedOldPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Entity for testing")
                .appointments(List.of())
                .build();

        PasswordUpdateDto dto = new PasswordUpdateDto();
        dto.setOldPassword("OldPass123@");
        dto.setNewPassword("NewPass123@");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(dto.getNewPassword())).thenReturn("encodedNewPass");

        userService.updatePassword(id, dto);

        verify(userRepo, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).matches("OldPass123@", "encodedOldPass");
        verify(passwordEncoder, times(1)).encode("NewPass123@");
    }

    @Test
    void updatePassword_shouldThrow400_whenOldPasswordIncorrect() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedOldPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Entity for testing")
                .appointments(List.of())
                .build();
        PasswordUpdateDto dto = new PasswordUpdateDto();
        dto.setOldPassword("WrongPass123@");
        dto.setNewPassword("NewPass123@");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getOldPassword(), user.getPassword())).thenReturn(false);

        UserException ex = assertThrows(UserException.class, () -> userService.updatePassword(id, dto));

        assertEquals(400, ex.getErrorCode());
        verify(userRepo, never()).save(any());
        verify(passwordEncoder, times(1)).matches("WrongPass123@", "encodedOldPass");
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void updatePassword_shouldThrow404_whenUserNotFound() {
        UUID id = UUID.randomUUID();
        PasswordUpdateDto dto = new PasswordUpdateDto();
        dto.setOldPassword("OldPass123@");
        dto.setNewPassword("NewPass123@");


        when(userRepo.findById(id)).thenReturn(Optional.empty());

        UserException ex = assertThrows(UserException.class, () -> userService.updatePassword(id, dto));

        assertEquals(404, ex.getErrorCode());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepo, never()).save(any());
    }

}
