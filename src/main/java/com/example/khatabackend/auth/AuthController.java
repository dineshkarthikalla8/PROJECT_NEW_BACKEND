package com.example.khatabackend.auth;

import com.example.khatabackend.user.User;
import com.example.khatabackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // ✅ allow frontend
public class AuthController {

    @Autowired
    private UserService userService;

    // ----------------- SIGNUP -----------------
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody User user) {
        try {
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Request body cannot be null"));
            }

            // validate inputs
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Name is required"));
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Email is required"));
            }
            if (user.getMobile() == null || user.getMobile().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Mobile number is required"));
            }
            if (user.getGender() == null || user.getGender().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Gender is required"));
            }
            if (user.getDob() == null) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Date of birth is required"));
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("error", "Password is required"));
            }

            // save user
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(new LoginResponse(savedUser.getId())); // ✅ return userId

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse("error", "Email or mobile number already exists"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("error", "Server error: " + e.getMessage()));
        }
    }

    // ----------------- LOGIN -----------------
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("error", e.getMessage()));
        }
    }
}
