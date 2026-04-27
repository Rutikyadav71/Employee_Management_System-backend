package com.rutik.ems.controller;
import com.rutik.ems.dto.*;
import com.rutik.ems.model.Admin;
import com.rutik.ems.repository.AdminRepository;
import com.rutik.ems.security.JwtUtil;
import com.rutik.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins="https://ry-ems.vercel.app")
public class AdminController {

    @Autowired AdminRepository adminRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired AuthService authService;
    @Autowired JwtUtil jwtUtil;

    // Public register: ONLY when zero admins exist (first admin)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (adminRepository.count() >= 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Registration closed. Ask your system administrator to add you via Admin Control.");
        }
        Admin a = buildAdmin(req); a.setIsMain(true);
        adminRepository.save(a);
        return ResponseEntity.ok("Main admin registered successfully");
    }

    // Admin-panel register: authenticated MAIN admin can add up to 4 total
    @PostMapping("/register-by-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerByAdmin(@RequestBody RegisterRequest req) {
        if (adminRepository.count() >= 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Maximum of 4 admins reached.");
        }
        if (adminRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered.");
        }
        // Check that the caller is main admin
        Admin a = buildAdmin(req); a.setIsMain(false);
        adminRepository.save(a);
        return ResponseEntity.ok(Map.of("message","Admin added successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Admin admin = authService.adminLogin(req);
            String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole());
            AuthResponse resp = new AuthResponse(token, admin.getRole(), admin.getName(), admin.getEmail(), admin.getId(), true);
            resp.setIsMain(admin.getIsMain());
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && msg.startsWith("ACCOUNT_FROZEN"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(msg);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg);
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<?> adminExists() {
        long count = adminRepository.count();
        return ResponseEntity.ok(Map.of("exists", count > 0, "full", count >= 4, "firstTime", count == 0));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> list = adminRepository.findAll();
        list.forEach(a -> a.setPassword(null));
        return ResponseEntity.ok(list);
    }

    // Only main admin can freeze/unfreeze/delete others
    @PutMapping("/{id}/freeze")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> freeze(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        return withMainAdminCheck(authHeader, () -> adminRepository.findById(id).map(a -> {
            if (a.getIsMain()) return ResponseEntity.badRequest().body((Object)"Cannot freeze main admin");
            a.setFrozen(true); adminRepository.save(a);
            return ResponseEntity.ok(Map.of("message","Admin frozen"));
        }).orElse(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}/unfreeze")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unfreeze(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        return withMainAdminCheck(authHeader, () -> adminRepository.findById(id).map(a -> {
            a.setFrozen(false); adminRepository.save(a);
            return ResponseEntity.ok(Map.of("message","Admin unfrozen"));
        }).orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        return withMainAdminCheck(authHeader, () -> {
            if (!adminRepository.existsById(id)) return ResponseEntity.notFound().build();
            Admin a = adminRepository.findById(id).get();
            if (a.getIsMain()) return ResponseEntity.badRequest().body("Cannot delete main admin");
            adminRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message","Admin deleted"));
        });
    }

    private ResponseEntity<?> withMainAdminCheck(String authHeader, java.util.function.Supplier<ResponseEntity<?>> action) {
        String token = authHeader.replace("Bearer ","");
        String email = jwtUtil.extractEmail(token);
        Admin caller = adminRepository.findByEmail(email).orElse(null);
        if (caller == null || !caller.getIsMain())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the main administrator can perform this action");
        return action.get();
    }

    private Admin buildAdmin(RegisterRequest req) {
        Admin a = new Admin();
        a.setName(req.getName()); a.setEmail(req.getEmail());
        a.setPassword(passwordEncoder.encode(req.getPassword()));
        a.setRole("ROLE_ADMIN"); a.setFrozen(false);
        return a;
    }
}