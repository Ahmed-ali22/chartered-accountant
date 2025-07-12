package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.model.dto.appointment.AppointmentDto;
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
        this.userRepo = userRepo;
    }

    @Override
    public void save(AppointmentDto appointmentDto) {
        User user = userRepo.findByEmail(appointmentDto.getUserEmail())
                .orElseThrow(()-> new IllegalArgumentException("User Not Found"));

        if (appointmentRepo.existsByUserAndDateTime(user,appointmentDto.getDateTime())) {
            throw new IllegalArgumentException("Appointment already exists for this date");
        }
        Appointment appointment = AppointmentMapper.toEntity(appointmentDto,user);
        appointmentRepo.save(appointment);
        log.info("New Appointment Created by User Email :{}", appointmentDto.getUserEmail());
    }

    @Override
    public void update(AppointmentUpdateDto appointmentUpdateDto, UUID id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Appointment  Not Found"));
        appointmentRepo.save(AppointmentMapper.updateEntityFromDto(appointmentUpdateDto,appointment));
        log.info("Appointment of User Email : {} successfully Updated  ", appointmentUpdateDto.getUserEmail());
    }

    @Override
    public List<Appointment> findByUserEmail(String userEmail) {
        return List.of();
    }

    @Override
    public List<Appointment> findAll() {
        return List.of();
    }
}
