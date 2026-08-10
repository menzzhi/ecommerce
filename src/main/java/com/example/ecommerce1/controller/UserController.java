package com.example.ecommerce1.controller;

import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequest createUserRequest){
        userService.createUser(createUserRequest);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUser(@RequestParam int page,
                                                         @RequestParam int items){
        Page<UserResponse> allUsers = userService.getAllUsers(page, items);
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> deleteUserById(@RequestParam Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserInformation(@RequestBody UserUpdate userUpdate,
                                                        JwtAuthenticationToken token){
        userService.updateEmailAndName(userUpdate, token);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }
}
