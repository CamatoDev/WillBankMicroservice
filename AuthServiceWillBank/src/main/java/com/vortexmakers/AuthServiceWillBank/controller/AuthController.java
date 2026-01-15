/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.AuthServiceWillBank.controller;

/**
 *
 * @author DELL
 */
import com.vortexmakers.AuthServiceWillBank.dto.AuthResponse;
import com.vortexmakers.AuthServiceWillBank.dto.LoginRequest;
import com.vortexmakers.AuthServiceWillBank.dto.RegisterRequest;
import com.vortexmakers.AuthServiceWillBank.entity.User;
import com.vortexmakers.AuthServiceWillBank.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok("Utilisateur créé avec succès : " + user.getUsername());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean isValid = authService.validateToken(token);
            return ResponseEntity.ok(isValid);
        }
        return ResponseEntity.badRequest().body("Token manquant ou invalide");
    }
}
