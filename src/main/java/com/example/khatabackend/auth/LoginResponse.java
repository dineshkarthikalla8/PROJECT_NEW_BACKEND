package com.example.khatabackend.auth;

public class LoginResponse {

    private String status;
    private String message;
    private Long userId;
    private String name;
    private String email;

    // Constructor for status + message
    public LoginResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor for only userId
    public LoginResponse(Long userId) {
        this.userId = userId;
    }

    // ✅ New constructor for id + name + email
    public LoginResponse(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    // --- Getters and Setters ---
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
