package com.rutik.ems.service;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.dto.RegisterRequest;
import com.rutik.ems.model.Admin;
import com.rutik.ems.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest req) {
        if (adminRepo.findByEmail(req.getEmail()).isPresent()) {
            return "❌ Email already exists";
        }

        Admin admin = new Admin();
        admin.setName(req.getName());
        admin.setEmail(req.getEmail());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));

        adminRepo.save(admin);
        return "✅ Admin registered successfully";
    }

    @Override
    public Admin login(LoginRequest req) {
        Admin admin = adminRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("❌ Admin not found"));

        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw new RuntimeException("❌ Invalid password");
        }

        return admin;
    }
}
