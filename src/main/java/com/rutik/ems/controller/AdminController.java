package com.rutik.ems.controller;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.dto.RegisterRequest;
import com.rutik.ems.model.Admin;
import com.rutik.ems.repository.AdminRepository;
import com.rutik.ems.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "https://ry-ems.vercel.app") // Adjust frontend origin if needed
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Admin Registration - allows only one admin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (adminRepository.count() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("❌ Admin already registered.");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        adminRepository.save(admin);

        return ResponseEntity.ok("✅ Admin registered successfully.");
    }

    // ✅ Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Admin admin = adminService.login(request);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<?> checkAdminExists() {
        boolean exists = adminRepository.count() > 0;
        return ResponseEntity.ok(Map.of("exists", exists));
    }

}
