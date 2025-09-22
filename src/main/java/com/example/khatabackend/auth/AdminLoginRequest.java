package com.example.khatabackend.auth;

public class AdminLoginRequest {
    private String username;
    private String password;

    // Default constructor (needed for JSON deserialization)
    public AdminLoginRequest() {}

    // Parameterized constructor (optional)
    public AdminLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
