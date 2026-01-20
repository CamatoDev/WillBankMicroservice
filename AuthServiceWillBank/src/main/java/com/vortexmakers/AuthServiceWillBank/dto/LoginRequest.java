/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vortexmakers.AuthServiceWillBank.dto;

/**
 *
 * @author DELL
 */
public class LoginRequest {

    private String usernameOrEmail;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    // GETTERS et SETTERS

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    // For backward compatibility
    public String getUsername() {
        return usernameOrEmail;
    }

    public void setUsername(String username) {
        this.usernameOrEmail = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
