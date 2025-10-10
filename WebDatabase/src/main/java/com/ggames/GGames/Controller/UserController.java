package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = userService.registerUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("User registered successfully: " + newUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        boolean valid = userService.validateCredentials(user.getUsername(), user.getPassword());
        if (valid) {
            return ResponseEntity.ok().build();
        } else {
            // Always return generic error to avoid leaking info
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
