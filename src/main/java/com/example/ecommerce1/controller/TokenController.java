package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.LoginRequest;
import com.example.ecommerce1.dto.LoginResponse;
import com.example.ecommerce1.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = tokenService.login(loginRequest);
        return ResponseEntity.ok(login);
    }
}
