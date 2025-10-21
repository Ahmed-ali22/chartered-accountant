package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.error.exception.AppointmentException;
import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.appointment.AppointmentResponseDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentSaveDto;
import com.example.chartered_accountant.model.dto.appointment.AppointmentUpdateDto;
import com.example.chartered_accountant.model.entity.Appointment;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.AppointmentRepo;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.mapper.AppointmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final UserRepo userRepo;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, UserRepo userRepo) {
        this.appointmentRepo = appointmentRepo;
        log.info(" Appointment Repository injected successfully ");
        this.userRepo = userRepo;
        log.info(" User Repository injected successfully ");
    }

    @Override
    public AppointmentResponseDto save(AppointmentSaveDto appointmentSaveDto) {
        User user = userRepo.findByEmail(appointmentSaveDto.getUserEmail())
                .orElseThrow(()-> new UserException(404, "userNotFound", "User not found with email: test@example.com"));

        if (appointmentRepo.existsByDateTime(appointmentSaveDto.getDateTime())) {
            throw new AppointmentException(
                    409, "appointmentConflict", "Appointment already exists for date: " + appointmentSaveDto.getDateTime()
            );
        }
        Appointment appointment = AppointmentMapper.saveToEntity(appointmentSaveDto,user);
       Appointment SavedAppointment = appointmentRepo.save(appointment);
        log.info("New Appointment Created by User Email :{}", appointmentSaveDto.getUserEmail());
        return AppointmentMapper.responseToDto(SavedAppointment);
    }

    @Override
    public AppointmentResponseDto update(AppointmentUpdateDto appointmentUpdateDto, UUID id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new AppointmentException(
                        404, "appointmentNotFound","Appointment with id " + id + " does not exist"
                ));
       Appointment updatedAppointment = appointmentRepo
               .save(AppointmentMapper.updateToEntity(appointmentUpdateDto, appointment));
       log.info("Appointment of User Email : {} successfully Updated  ", appointment.getUser().getEmail());
       return AppointmentMapper.responseToDto(updatedAppointment);
    }


    @Override
    public List<AppointmentResponseDto> findByUserEmail(String userEmail) {
        List<Appointment> appointments = appointmentRepo.findByUserEmail(userEmail);
        if(appointments.isEmpty()) {
            throw new AppointmentException(
                    404, "noAppointmentsForUser", "No appointments found for user email: " + userEmail
            );
        }
        log.info("Appointments of User Email : {} successfully Founded  ", userEmail);
        return appointments.stream().map(AppointmentMapper :: responseToDto).toList();
    }

    @Override
    public List<AppointmentResponseDto> findAll() {
        List<Appointment> appointments = appointmentRepo.findAll();
        if(appointments.isEmpty()) {
            throw new AppointmentException(
                    404, "noAppointments", "No appointments exist in the system"
            );
        }
        log.info("All Appointments Successfully Founded");
        return appointments.stream().map(AppointmentMapper :: responseToDto).toList();
    }
}
