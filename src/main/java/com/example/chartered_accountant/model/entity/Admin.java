package com.example.chartered_accountant.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private UUID id ;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
