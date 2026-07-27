package com.example.chartered_accountant.repository;

import com.example.chartered_accountant.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken , UUID> {

    Optional<RefreshToken> findByToken(String Token);
    void deleteByOwnerId(UUID ownerId);
}
