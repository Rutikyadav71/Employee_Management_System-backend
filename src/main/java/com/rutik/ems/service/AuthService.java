package com.rutik.ems.service;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.model.Employee;
import com.rutik.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login logic only - registration removed
    public Employee login(LoginRequest req) {
        return employeeRepo.findByEmail(req.getEmail())
                .filter(emp -> passwordEncoder.matches(req.getPassword(), emp.getPassword()))
                .orElseThrow(() -> new RuntimeException("❌ Invalid email or password"));
    }
}
