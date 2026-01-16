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
            "http://localhost:5173",        // frontend React Native
            "http://localhost:5174",        // frontend React Native
            "http://localhost:3000",        // Autre frontend potentiel
            "http://localhost:8081"         // Pour les tests
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