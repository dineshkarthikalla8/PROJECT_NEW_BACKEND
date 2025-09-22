package com.example.khatabackend.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    // ✅ Generic method (for any type of email)
    public void sendConfirmationMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dineshkarthikalla@gmail.com"); // must match spring.mail.username
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        logger.info("✅ Email sent successfully to {}", to);
    }

    // ✅ Specific method for registration email
    public void sendRegistrationEmail(String to, String name) {
        String subject = "Registration Successful";
        String body = "Hello " +"MR/MRS"+ name + ", 🎉 your registration at Online Grocery Store was successful! 🛒\\\\n" +  
        "Thank you for joining us.\n" +  "- Team Dineshkarthik Alla";
        sendConfirmationMail(to, subject, body);
    }
}
