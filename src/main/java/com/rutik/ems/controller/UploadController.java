package com.rutik.ems.controller;
import com.rutik.ems.model.Employee;
import com.rutik.ems.model.Admin;
import com.rutik.ems.repository.EmployeeRepository;
import com.rutik.ems.repository.AdminRepository;
import com.rutik.ems.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins="http://localhost:5173")
public class UploadController {
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private AdminRepository adminRepository;

    @PostMapping(value="/profile/{empId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadEmployeeProfile(@PathVariable int empId,
            @RequestParam("file") MultipartFile file) {
        if (file==null||file.isEmpty()) return ResponseEntity.badRequest().body("No file provided");
        try {
            String url = cloudinaryService.uploadProfileImage(file,"emp_"+empId);
            employeeRepository.findByEmpId(empId).ifPresent(emp -> {
                emp.setProfileImageUrl(url); employeeRepository.save(emp);
            });
            return ResponseEntity.ok(Map.of("url",url));
        } catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
        catch (IOException e) { return ResponseEntity.internalServerError().body("Upload failed: "+e.getMessage()); }
    }

    @PostMapping(value="/admin-profile/{adminId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAdminProfile(@PathVariable int adminId,
            @RequestParam("file") MultipartFile file) {
        if (file==null||file.isEmpty()) return ResponseEntity.badRequest().body("No file provided");
        try {
            String url = cloudinaryService.uploadProfileImage(file,"admin_"+adminId);
            adminRepository.findById(adminId).ifPresent(admin -> {
                admin.setProfileImageUrl(url); adminRepository.save(admin);
            });
            return ResponseEntity.ok(Map.of("url",url));
        } catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
        catch (IOException e) { return ResponseEntity.internalServerError().body("Upload failed: "+e.getMessage()); }
    }

    @PostMapping(value="/chat", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadChatFile(@RequestParam("file") MultipartFile file,
            @RequestParam("sender") String sender) {
        if (file==null||file.isEmpty()) return ResponseEntity.badRequest().body("No file provided");
        try { return ResponseEntity.ok(cloudinaryService.uploadChatFile(file,sender)); }
        catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
        catch (IOException e) { return ResponseEntity.internalServerError().body("Upload failed: "+e.getMessage()); }
    }
}