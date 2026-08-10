package com.example.ecommerce1.service;

import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.LoginRequest;
import com.example.ecommerce1.dto.LoginResponse;
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
public class TokenService {

    private UserRepository userRepository;
    private JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder passwordEncoder;

    public TokenService(UserRepository userRepository,
                        JwtEncoder jwtEncoder,
                        BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean matches = passwordEncoder.matches(loginRequest.password(), user.getSenha());

        if (!matches){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        long expiresAt = 300;

        JwtClaimsSet payload = JwtClaimsSet
                .builder()
                .issuer("myapplication")
                .subject(user.getEmail())
                .claim("scope", user.getRole().stream().findFirst())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresAt))
                .build();

        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(payload)).getTokenValue();

        return new LoginResponse(tokenValue, expiresAt);
    }
}
