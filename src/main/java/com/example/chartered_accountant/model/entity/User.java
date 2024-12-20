package com.example.chartered_accountant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number" , nullable = false)
    private String phone;

    @Column(name = "company_name")
    private String companyName;

    @Column
    private String description;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    private List<Appointment> appointments ;
}
