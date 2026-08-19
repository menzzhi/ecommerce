package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.RefreshToken;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Long expiresAt = 604800000L;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken generateRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(expiresAt),
                user
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken isRefreshTokenValid(RefreshToken refreshToken){
        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void revokeRefreshToken(String token){
        findByToken(token).ifPresent(
                rt -> {
                    rt.setRevoked(true);
                    refreshTokenRepository.save(rt);
                }
        );
    }
}
