package com.example.ecommerce1.controller;

import com.example.ecommerce1.domain.User;
import com.example.ecommerce1.dto.*;
import com.example.ecommerce1.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest createUserRequest){
        userService.createUser(createUserRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUser(@RequestParam int page,
                                                         @RequestParam int items){
        Page<UserResponse> allUsers = userService.getAllUsers(page, items);
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserUpdate> updateUserInformation(@RequestBody UserUpdate userUpdate,
                                                              @PathVariable Long userId){
        UserUpdate usrUpdated = userService.updateEmailAndName(userUpdate, userId);

        return ResponseEntity.ok(usrUpdated);
    }
}
