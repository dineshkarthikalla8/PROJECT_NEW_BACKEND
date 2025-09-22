package com.example.khatabackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // ✅ allow frontend
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    // 🚀 Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🚀 Test email manually (optional)
    @GetMapping("/sendMail")
    public String sendMail(@RequestParam String to) {
        userService.getEmailService().sendConfirmationMail(
                to,
                "Confirmation Mail",
                "Your account has been created successfully!"
        );
        return "Mail Sent to " + to;
    }
}
