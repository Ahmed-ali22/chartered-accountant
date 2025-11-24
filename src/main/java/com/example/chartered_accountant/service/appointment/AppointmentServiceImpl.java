package com.example.chartered_accountant.service.appointment;

import com.example.chartered_accountant.error.exception.AppointmentException;
import com.example.chartered_accountant.error.exception.UserException;
import com.example.chartered_accountant.model.dto.appointment.AppointmentRequestDto;
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
    public Appointment save(UUID userId, AppointmentRequestDto appointmentRequestDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new UserException(
                        404, "userNotFound", "User not found with ID: "+ userId
                        ));

        if (appointmentRepo.existsByDateTime(appointmentRequestDto.getDateTime())) {
            throw new AppointmentException(
                    409, "appointmentConflict", "Appointment already exists for date: " + appointmentRequestDto.getDateTime()
            );
        }
        Appointment appointment = AppointmentMapper.toAppointmentEntity(appointmentRequestDto,user);
       Appointment savedAppointment = appointmentRepo.save(appointment);
        log.info("New Appointment Created by User Email :{}", user.getEmail());
        return savedAppointment;
    }

    @Override
    public Appointment updateForUser(UUID appointmentId,UUID userId,AppointmentRequestDto appointmentRequestDto ) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(
                        404, "appointmentNotFound","Appointment with id " + appointmentId + " does not exist"
                ));
        if (!appointment.getUser().getId().equals(userId)) {
            throw new AppointmentException(403, "forbiddenAccess", "You cannot update this appointment");
        }
       Appointment updatedAppointment = appointmentRepo
               .save(AppointmentMapper.updateToEntity(appointmentRequestDto, appointment));
       log.info("Appointment of User Email : {} successfully Updated  ", appointment.getUser().getEmail());
       return updatedAppointment;
    }

    @Override
    public Appointment updateForAdmin(UUID appointmentId, AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(
                        404, "appointmentNotFound","Appointment with id " + appointmentId + " does not exist"
                ));

        Appointment updatedAppointment = appointmentRepo
                .save(AppointmentMapper.updateToEntity(appointmentRequestDto, appointment));
        log.info("Appointment with id {} successfully Updated by Admin", appointmentId);
        return updatedAppointment;
    }


    @Override
    public List<Appointment> findByUserId(UUID userId) {
        List<Appointment> appointments = appointmentRepo.findByUserId(userId);
        if(appointments.isEmpty()) {
            throw new AppointmentException(
                    404, "noAppointmentsForUser", "No appointments found for user Id: " + userId
            );
        }
        log.info("Appointments of User Email : {} successfully Founded  ", userId);
        return appointments;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = appointmentRepo.findAll();
        if(appointments.isEmpty()) {
            throw new AppointmentException(
                    404, "noAppointments", "No appointments exist in the system"
            );
        }
        log.info("All Appointments Successfully Founded");
        return appointments;
    }

    @Override
    public void deleteById(UUID appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(
                        404, "appointmentNotFound","Appointment with ID " + appointmentId + " does not exist"
                ));

        appointmentRepo.delete(appointment);
        log.info("Appointment Successfully Deleted With ID : {}",appointment.getId());
    }
}
