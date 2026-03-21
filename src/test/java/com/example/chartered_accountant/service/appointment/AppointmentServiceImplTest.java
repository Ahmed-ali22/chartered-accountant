package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.error.exception.AppointmentException;
import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.AppointmentRepo;
import com.example.chartered_accountant.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {
    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void save_shouldSaveAppointment_whenUserExistsAndNoTimeConflict() {
        UUID userId = UUID.randomUUID();
        LocalDateTime appointmentDateTime = LocalDateTime.now().plusDays(1);

        User user = User.builder()
                .id(userId)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(appointmentDateTime)
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateTime(appointmentDateTime)
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(appointmentRepo.existsByDateTime(appointmentDateTime)).thenReturn(false);
        when(appointmentRepo.save(any(Appointment.class))).thenReturn(appointment);

        appointmentService.save(userId, dto);

        verify(userRepo, times(1)).findById(userId);
        verify(appointmentRepo, times(1)).existsByDateTime(appointmentDateTime);
        verify(appointmentRepo, times(1)).save(any(Appointment.class));
    }
    @Test
    void save_shouldThrow404_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        LocalDateTime appointmentDateTime = LocalDateTime.now().plusDays(1);

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(appointmentDateTime)
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        UserException ex = assertThrows(UserException.class, () -> appointmentService.save(userId, dto));

        assertEquals(404, ex.getErrorCode());
        verify(appointmentRepo, never()).save(any());
    }
    @Test
    void save_shouldThrow409_whenAppointmentTimeAlreadyExists() {
        UUID userId = UUID.randomUUID();
        LocalDateTime appointmentDateTime = LocalDateTime.now().plusDays(1);

        User user = User.builder()
                .id(userId)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(appointmentDateTime)
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(appointmentRepo.existsByDateTime(appointmentDateTime)).thenReturn(true);

        AppointmentException ex = assertThrows(AppointmentException.class, () -> appointmentService.save(userId, dto));

        assertEquals(409, ex.getErrorCode());
        verify(appointmentRepo, never()).save(any());
    }

    @Test
    void updateForUser_shouldUpdateAppointment_whenUserIsOwner() {
        UUID userId = UUID.randomUUID();
        UUID appointmentId = UUID.randomUUID();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);

        User user = User.builder()
                .id(userId)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment existingAppointment = Appointment.builder()
                .id(appointmentId)
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(newDateTime)
                .status("scheduled")
                .service("Audit Service")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepo.save(any(Appointment.class))).thenReturn(existingAppointment);

        appointmentService.updateForUser(appointmentId, userId, dto);

        verify(appointmentRepo, times(1)).findById(appointmentId);
        verify(appointmentRepo, times(1)).save(any(Appointment.class));
    }

    @Test
    void updateForUser_shouldThrow404_whenAppointmentNotFound() {
        UUID userId = UUID.randomUUID();
        UUID appointmentId = UUID.randomUUID();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(newDateTime)
                .status("scheduled")
                .service("Audit Service")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.empty());

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.updateForUser(appointmentId, userId, dto));

        assertEquals(404, ex.getErrorCode());
        verify(appointmentRepo, never()).save(any());
    }

    @Test
    void updateForUser_shouldThrow403_whenUserIsNotOwner() {
        UUID userId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();
        UUID appointmentId = UUID.randomUUID();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);

        User user = User.builder()
                .id(userId)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment existingAppointment = Appointment.builder()
                .id(appointmentId)
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(newDateTime)
                .status("scheduled")
                .service("Audit Service")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.updateForUser(appointmentId, differentUserId, dto));

        assertEquals(403, ex.getErrorCode());
        verify(appointmentRepo, never()).save(any());
    }
    @Test
    void updateForAdmin_shouldUpdateAppointment_whenAdminRequest() {
        UUID appointmentId = UUID.randomUUID();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment existingAppointment = Appointment.builder()
                .id(appointmentId)
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(newDateTime)
                .status("completed")
                .service("Tax Consultation")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepo.save(any(Appointment.class))).thenReturn(existingAppointment);

        appointmentService.updateForAdmin(appointmentId, dto);

        verify(appointmentRepo, times(1)).findById(appointmentId);
        verify(appointmentRepo, times(1)).save(any(Appointment.class));
    }

    @Test
    void updateForAdmin_shouldThrow404_whenAppointmentNotFound() {
        UUID appointmentId = UUID.randomUUID();
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);

        AppointmentRequestDto dto = AppointmentRequestDto.builder()
                .dateTime(newDateTime)
                .status("completed")
                .service("Tax Consultation")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.empty());

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.updateForAdmin(appointmentId, dto));

        assertEquals(404, ex.getErrorCode());
        verify(appointmentRepo, never()).save(any());
    }
    @Test
    void findByUserId_shouldReturnAppointments_whenUserHasAppointments() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(appointmentRepo.findByUserId(userId)).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.findByUserId(userId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findByUserId_shouldThrow404_whenUserHasNoAppointments() {
        UUID userId = UUID.randomUUID();

        when(appointmentRepo.findByUserId(userId)).thenReturn(List.of());

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.findByUserId(userId));

        assertEquals(404, ex.getErrorCode());
    }

    @Test
    void findAll_shouldReturnAllAppointments_whenAppointmentsExist() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(appointmentRepo.findAll()).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findAll_shouldThrow404_whenNoAppointmentsExist() {
        when(appointmentRepo.findAll()).thenReturn(List.of());

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.findAll());

        assertEquals(404, ex.getErrorCode());
    }
    @Test
    void deleteById_shouldDeleteAppointment_whenAppointmentExists() {
        UUID appointmentId = UUID.randomUUID();

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Ahmed")
                .email("ahmed@example.com")
                .password("encodedPass")
                .phoneNumber("+201012345678")
                .companyName("MyCompany")
                .description("Test user")
                .build();

        Appointment appointment = Appointment.builder()
                .id(appointmentId)
                .user(user)
                .dateTime(LocalDateTime.now().plusDays(1))
                .status("scheduled")
                .service("Tax Consultation")
                .build();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(appointment));

        appointmentService.deleteById(appointmentId);

        verify(appointmentRepo, times(1)).delete(appointment);
    }

    @Test
    void deleteById_shouldThrow404_whenAppointmentNotFound() {
        UUID appointmentId = UUID.randomUUID();

        when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.empty());

        AppointmentException ex = assertThrows(AppointmentException.class,
                () -> appointmentService.deleteById(appointmentId));

        assertEquals(404, ex.getErrorCode());
        verify(appointmentRepo, never()).delete(any());
    }
}
