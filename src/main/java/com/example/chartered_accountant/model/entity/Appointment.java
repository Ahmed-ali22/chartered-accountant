package com.example.chartered_accountant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID )
    @Column( updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String status = "scheduled";

    @Column(nullable = false)
    private String service;
}
