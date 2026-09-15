package com.healthmate.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthmate.model.ERole;
import com.healthmate.model.Role;
import com.healthmate.model.User;
import com.healthmate.dto.JwtResponse;
import com.healthmate.dto.LoginRequest;
import com.healthmate.dto.MessageResponse;
import com.healthmate.dto.SignupRequest;
import com.healthmate.repository.RoleRepository;
import com.healthmate.repository.UserRepository;
import com.healthmate.config.JwtUtils;
import com.healthmate.service.OtpService;
import com.healthmate.service.UserDetailsImpl;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OtpService otpService;

    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        System.out.println("Checking availability for: " + username);
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.ok(new MessageResponse("Taken"));
        }
        return ResponseEntity.ok(new MessageResponse("Available"));
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        System.out.println("Checking availability for email: " + email);
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.ok(new MessageResponse("Taken"));
        }
        return ResponseEntity.ok(new MessageResponse("Available"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getProfilePhoto(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Set additional physical stats
        user.setAge(signUpRequest.getAge());
        user.setHeight(signUpRequest.getHeight());
        user.setWeight(signUpRequest.getWeight());
        user.setActivityLevel(signUpRequest.getActivityLevel());
        user.setHealthGoal(signUpRequest.getHealthGoal());

        // Streamlined role assignment (always ROLE_USER for public signup)
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.saveAndFlush(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/send-signup-otp")
    public ResponseEntity<?> sendSignupOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is required."));
        }
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully! (Dev Code: " + otp + ")"));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is required."));
        }
        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully! (Dev Code: " + otp + ")"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || !userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Registered email not found."));
        }
        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully! (Dev Code: " + otp + ")"));
    }

    @PostMapping("/forgot-username")
    public ResponseEntity<?> forgotUsername(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || !userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Registered email not found."));
        }
        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully! (Dev Code: " + otp + ")"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or Expired OTP."));
        }
        return ResponseEntity.ok(new MessageResponse("OTP verified successfully!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or Expired OTP."));
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        otpService.clearOtp(email);

        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }

    @PostMapping("/reset-username")
    public ResponseEntity<?> resetUsername(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newUsername = request.get("newUsername");

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or Expired OTP."));
        }

        if (userRepository.existsByUsername(newUsername)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        user.setUsername(newUsername);
        userRepository.save(user);
        otpService.clearOtp(email);

        return ResponseEntity.ok(new MessageResponse("Username reset successfully!"));
    }
}
