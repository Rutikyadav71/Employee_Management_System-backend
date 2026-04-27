package com.rutik.ems.service;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.model.Admin;
import com.rutik.ems.model.Employee;
import com.rutik.ems.repository.AdminRepository;
import com.rutik.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private AdminRepository adminRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Admin adminLogin(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid admin credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid admin credentials");
        }

        if (Boolean.TRUE.equals(admin.getFrozen())) {
            throw new RuntimeException("ACCOUNT_FROZEN: Your admin account has been frozen. Contact the other administrator.");
        }

        return admin;
    }

    public Employee employeeLogin(LoginRequest request) {
        Employee emp = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid employee credentials"));

        if (!passwordEncoder.matches(request.getPassword(), emp.getPassword())) {
            throw new RuntimeException("Invalid employee credentials");
        }

        return emp;
    }
}
