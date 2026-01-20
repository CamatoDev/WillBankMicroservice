/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.APIGatewayWillBank.config;

/**
 *
 * @author DELL
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // Configuration des origines autorisées
        corsConfig.setAllowedOrigins(Arrays.asList(
            "http://127.0.0.1:*",           // Tous les ports 127.0.0.1
            "http://localhost:5173",        // frontend React Native (Admin)
            "http://localhost:5174",        // frontend React Native (Client)
            "http://localhost:3000",        // Autre frontend potentiel
            "http://localhost:8087",        // Expo Web
            "http://10.0.2.2:*",            // Android Emulator
            "http://192.168.*.*:*"          // Devices sur réseau local
        ));
        
        // Configuration des méthodes HTTP autorisées
        corsConfig.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Configuration des headers autorisés
        corsConfig.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "X-Requested-With", 
            "Accept", 
            "Origin", 
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Configuration des headers exposés
        corsConfig.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin", 
            "Access-Control-Allow-Credentials"
        ));
        
        // Autoriser les credentials (cookies, auth headers)
        corsConfig.setAllowCredentials(true);
        
        // Durée de mise en cache des pré-vérifications CORS
        corsConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsWebFilter(source);
    }
}