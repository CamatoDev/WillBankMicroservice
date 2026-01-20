/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.AuthServiceWillBank.service;

/**
 *
 * @author DELL
 */
import com.vortexmakers.AuthServiceWillBank.entity.User;
import com.vortexmakers.AuthServiceWillBank.repository.UserRepository;
import com.vortexmakers.AuthServiceWillBank.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder, 
                      JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Enregistrer un nouvel utilisateur
    public User register(String username, String email, String password) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username déjà utilisé");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // Créer le nouvel utilisateur avec mot de passe hashé
        User user = new User(
                username,
                email,
                passwordEncoder.encode(password),
                "USER"
        );

        return userRepository.save(user);
    }

    // Authentifier et générer un token JWT
    public String login(String usernameOrEmail, String password) {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        // Générer et retourner le token JWT
        return jwtService.generateToken(user.getUsername(), user.getRole());
    }

    // Valider un token
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
