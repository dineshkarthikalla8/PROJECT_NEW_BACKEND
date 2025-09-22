package com.example.khatabackend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000") // ✅ allow frontend
public class AdminController {

    // Hardcoded login (replace with DB later)
    private static final String ADMIN_USERNAME = "karthik";
    private static final String ADMIN_PASSWORD = "8";

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminLoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (ADMIN_USERNAME.equals(request.getUsername()) &&
            ADMIN_PASSWORD.equals(request.getPassword())) {
            response.put("status", "success");
            response.put("message", "Login successful ✅");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid credentials ❌");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
