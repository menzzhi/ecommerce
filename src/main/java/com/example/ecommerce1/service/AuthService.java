package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.RefreshToken;
import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.RefreshTokenResponse;
import com.example.ecommerce1.dto.LoginRequest;
import com.example.ecommerce1.dto.AccessTokenResponse;
import com.example.ecommerce1.dto.RefreshTokenRequest;
import com.example.ecommerce1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class AuthService {

    private UserRepository userRepository;
    private JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository,
                       JwtEncoder jwtEncoder,
                       BCryptPasswordEncoder passwordEncoder,
                       RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public AccessTokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String roleName = user.getRole().stream().findFirst().get().getName();

        boolean matches = passwordEncoder.matches(loginRequest.password(), user.getSenha());

        if (!matches){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        long expiresAt = 300;

        JwtClaimsSet payload = JwtClaimsSet
                .builder()
                .issuer("myapplication")
                .subject(user.getEmail())
                .claim("scope", roleName)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresAt))
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(payload)).getTokenValue();
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);

        return new AccessTokenResponse(
                accessToken,
                expiresAt,
                refreshToken.getToken());
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.token())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        refreshTokenService.isRefreshTokenValid(refreshToken);

        User user = refreshToken.getUser();
        String accessToken = generateAccessToken(user);

        return new RefreshTokenResponse(
                refreshToken.getToken(),
                refreshToken.getExpiresAt().toEpochMilli(),
                accessToken);
    }

    public String generateAccessToken(User user){

        String roleName = user.getRole().stream().findFirst().get().getName();
        int expiresAt = 300;


        JwtClaimsSet payload = JwtClaimsSet
                .builder()
                .issuer("myapplication")
                .subject(user.getEmail())
                .claim("scope", roleName)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresAt))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(payload)).getTokenValue();
    }
}
