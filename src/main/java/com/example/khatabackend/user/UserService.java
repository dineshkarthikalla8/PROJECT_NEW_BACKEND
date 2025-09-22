package com.example.khatabackend.user;

import com.example.khatabackend.auth.LoginRequest;
import com.example.khatabackend.auth.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // @Autowired
    // private SmsService smsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Registration
 // ✅ Registration
    public User registerUser(User user) {
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered: " + user.getEmail());
        }

        if (user.getMobile() != null && userRepository.findByMobile(user.getMobile()).isPresent()) {
            throw new IllegalArgumentException("Mobile number already registered: " + user.getMobile());
        }

        if (user.getDob() == null) {
            throw new IllegalArgumentException("Date of Birth is required");
        }

        int age = Period.between(user.getDob(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("User must be 18 or older");
        }

        // ✅ Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser;
        try {
            savedUser = userRepository.save(user); // 👈 age is auto-calculated inside User entity
            logger.info("User saved successfully: {}", savedUser.getEmail());
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save user: duplicate or invalid data", e);
            throw new IllegalArgumentException("Duplicate data or invalid input", e);
        } catch (Exception e) {
            logger.error("Failed to save user", e);
            throw new RuntimeException("Failed to register user", e);
        }

        // ✅ Send confirmation email
        if (savedUser.getEmail() != null) {
            try {
                emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getName());
                logger.info("Confirmation email sent to {}", savedUser.getEmail());
            } catch (Exception e) {
                logger.error("Failed to send email to {}", savedUser.getEmail(), e);
            }
        }

        // ✅ Send confirmation SMS
        // if (savedUser.getMobile() != null) {
        //     try {
        //         String mobile = savedUser.getMobile().trim();
        //         if (!mobile.startsWith("+")) {
        //             mobile = "+91" + mobile;
        //         }

        //         smsService.sendSms(
        //                 mobile,
        //                 "Hello " + savedUser.getName() +
        //                         ", 🎉 your registration at Online Grocery Store was successful! 🛒\n" +
        //                         "Thank you for joining us.\n- Team Dineshkarthik Alla"
        //         );
        //         logger.info("Confirmation SMS sent to {}", mobile);
        //     } catch (Exception e) {
        //         logger.error("Failed to send SMS to {}", savedUser.getMobile(), e);
        //     }
        // }

        return savedUser;
    }



    // ✅ Fix getEmailService()
    public EmailService getEmailService() {
        return emailService;
    }

    // ✅ Fix login method
    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return new LoginResponse("error", "User not found");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse("error", "Invalid password");
        }

        return new LoginResponse(user.getId()); // success → return userId
    }

    public Optional<User> findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }
}
