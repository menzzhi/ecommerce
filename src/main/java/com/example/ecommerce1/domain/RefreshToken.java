package com.example.ecommerce1.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    @Column(unique = true)
    private String token;

    @Column(name = "expires_at")
    private Instant expiresAt;

    private boolean revoked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken() {
    }

    public RefreshToken(String token, Instant expiresAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public Long getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(Long refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
