package com.rutik.ems.controller;

import com.rutik.ems.dto.AuthResponse;
import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.dto.RegisterRequest;
import com.rutik.ems.model.Admin;
import com.rutik.ems.repository.AdminRepository;
import com.rutik.ems.security.JwtUtil;
import com.rutik.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register only ONE admin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (adminRepository.count() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("❌ Admin already registered");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole("ROLE_ADMIN");

        adminRepository.save(admin);

        return ResponseEntity.ok("✅ Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        Admin admin = authService.adminLogin(request);

        System.out.println("DEBUG ADMIN => " + admin);

        String token = jwtUtil.generateToken(
                admin.getEmail(),
                admin.getRole()
        );

        System.out.println("DEBUG TOKEN => " + token);

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        admin.getRole(),
                        admin.getName(),
                        admin.getEmail()
                )
        );
    }


    // ✅ Used by frontend
    @GetMapping("/exists")
    public ResponseEntity<?> adminExists() {
        return ResponseEntity.ok(
                Map.of("exists", adminRepository.count() > 0)
        );
    }
}
