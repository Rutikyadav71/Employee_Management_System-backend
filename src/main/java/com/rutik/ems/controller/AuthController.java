package com.rutik.ems.controller;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.model.Employee;
import com.rutik.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://ry-ems.vercel.app")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Employee emp = authService.login(req);
            // You may choose to hide sensitive info before sending (like password)
            emp.setPassword(null);  // prevent exposing hashed password to frontend
            return ResponseEntity.ok(emp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());  // 401 Unauthorized
        }
    }
}
