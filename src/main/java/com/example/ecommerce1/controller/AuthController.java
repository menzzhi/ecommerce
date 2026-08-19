package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.service.AuthService;
import com.example.ecommerce1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    public AuthController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest){
        AccessTokenResponse login = authService.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequest createUserRequest){
        userService.createUser(createUserRequest);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshTokenResponse refresh = authService.refresh(refreshTokenRequest);
        return ResponseEntity.ok(refresh);
    }
}
