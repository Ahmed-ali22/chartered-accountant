package com.example.chartered_accountant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// set name of user table in another quotes' bec. it reserved word in postgresql //
@Table(name = "\"user\"")
public class User extends BaseEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.UUID )
    @Column( name = "id",updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number" , nullable = false)
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column
    private String description;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    private List<Appointment> appointments ;
}
