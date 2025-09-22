// package com.example.khatabackend.user;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// @Service
// public class SmsService {

//     @Value("${twilio.account-sid}")
//     private String accountSid;

//     @Value("${twilio.auth-token}")
//     private String authToken;

//     @Value("${twilio.phone-number}")
//     private String fromNumber;

//     public void sendSms(String toNumber, String text) {
//         try {
//             Twilio.init(accountSid, authToken);
//             Message message = Message.creator(
//                     new com.twilio.type.PhoneNumber(toNumber),
//                     new com.twilio.type.PhoneNumber(fromNumber),
//                     text
//             ).create();

//             System.out.println("✅ SMS sent successfully! SID: " + message.getSid());
//         } catch (Exception e) {
//             System.err.println("❌ Failed to send SMS: " + e.getMessage());
//         }
//     }

// }
