package com.example.chartered_accountant.service.refreshtoken;

import com.example.chartered_accountant.error.exception.TokenException;
import com.example.chartered_accountant.model.entity.RefreshToken;
import com.example.chartered_accountant.repository.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
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
            throw new TokenException(
                    401, "Expired Session Token", "The refresh token has expired. Please log in again.");
        }
        return token;
    }

    @Transactional
    public void deleteByOwnerId(UUID ownerId) {
        refreshTokenRepo.deleteByOwnerId(ownerId);
    }
}
