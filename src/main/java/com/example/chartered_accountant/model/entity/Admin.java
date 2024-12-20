package com.example.chartered_accountant.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.UUID )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
