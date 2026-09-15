package com.healthmate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private static final long OTP_VALID_DURATION_MS = 10 * 60 * 1000; // 10 minutes
    private final SecureRandom random = new SecureRandom();
    private final Map<String, OtpData> otpMap = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private static class OtpData {
        final String code;
        final long expiryTime;

        OtpData(String code, long expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }
    }

    public String generateOtp(String email) {
        String code = String.format("%06d", random.nextInt(1000000));
        long expiry = System.currentTimeMillis() + OTP_VALID_DURATION_MS;
        otpMap.put(email.toLowerCase(), new OtpData(code, expiry));

        System.out.println("=================================================");
        System.out.println("[OTP SERVICE] Generated OTP for " + email + ": " + code);
        System.out.println("=================================================");

        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("HealthMate - Your Verification OTP Code");
                message.setText("Your OTP verification code for HealthMate is: " + code + "\n\nThis code will expire in 10 minutes.");
                mailSender.send(message);
                System.out.println("[OTP SERVICE] Email sent successfully to " + email);
            } catch (Exception e) {
                System.err.println("[OTP SERVICE] Could not send live email (SMTP not configured or invalid credentials). Dev OTP Code is: " + code);
            }
        }

        return code;
    }

    public boolean validateOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        OtpData data = otpMap.get(email.toLowerCase());
        if (data == null) {
            return false;
        }
        if (System.currentTimeMillis() > data.expiryTime) {
            otpMap.remove(email.toLowerCase());
            return false;
        }
        return data.code.equals(otp.trim());
    }

    public void clearOtp(String email) {
        if (email != null) {
            otpMap.remove(email.toLowerCase());
        }
    }
}
