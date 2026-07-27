package com.example.chartered_accountant.service.refreshtoken;

import com.example.chartered_accountant.model.entity.RefreshToken;
import com.example.chartered_accountant.repository.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class RefreshTokenService {
    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpiryMs;

    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService( RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(UUID ownerId) {

        refreshTokenRepo.deleteByOwnerId(ownerId);

        RefreshToken refreshToken = RefreshToken.builder()
                .ownerId(ownerId)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpiryMs))
                .build();

        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request.");
        }
        return token;
    }

    @Transactional
    public void deleteByOwnerId(UUID ownerId) {
        refreshTokenRepo.deleteByOwnerId(ownerId);
    }
}
