package com.rutik.ems.controller;

import com.rutik.ems.dto.AuthResponse;
import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.model.Employee;
import com.rutik.ems.security.JwtUtil;
import com.rutik.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Employee emp = authService.employeeLogin(request);
            String token = jwtUtil.generateToken(emp.getEmail(), emp.getRole());

            AuthResponse resp = new AuthResponse(
                    token,
                    emp.getRole(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getEmpId(),
                    emp.getDepartment(),
                    emp.getSalary()
            );
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
