package com.rutik.ems.service;

import com.rutik.ems.model.Employee;
import com.rutik.ems.repository.EmployeeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Employee> getAll() {
        return repo.findAll();
    }

    @Override
    public Employee getById(int empId) {
        return repo.findByEmpId(empId).orElse(null);
    }

    @Override
    public Employee create(Employee emp) {
        // ✅ Save plain password for email before encoding
        String plainPassword = emp.getPassword();

        // ✅ Encode the password
        emp.setPassword(passwordEncoder.encode(plainPassword));

        // ✅ Save employee
        Employee savedEmp = repo.save(emp);

        // ✅ Send email
        try {
            sendWelcomeEmail(emp.getEmail(), emp.getName(), plainPassword);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email to " + emp.getEmail());
            e.printStackTrace();
        }

        return savedEmp;
    }

    @Override
    public Employee update(int empId, Employee emp) {
        Employee existing = repo.findByEmpId(empId).orElse(null);
        if (existing != null) {
            existing.setName(emp.getName());
            existing.setDepartment(emp.getDepartment());
            existing.setEmail(emp.getEmail());
            existing.setSalary(emp.getSalary());

            if (emp.getPassword() != null && !emp.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(emp.getPassword()));
            }

            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void delete(int empId) {
        Employee employee = repo.findByEmpId(empId).orElse(null);
        if (employee != null) {
            repo.delete(employee);
        } else {
            System.out.println("Employee not found with empId: " + empId);
        }
    }

    // ✅ Send email method
    private void sendWelcomeEmail(String toEmail, String name, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Welcome to EMS - Your Login Credentials");
        helper.setText(
                "<p>Hello <strong>" + name + "</strong>,</p>" +
                        "<p>You have been successfully added as an employee to our EMS system.</p>" +
                        "<p><strong>Email:</strong> " + toEmail + "<br>" +
                        "<strong>Password:</strong> " + password + "</p>" +
                        "<p>Regards,<br>EMS Admin</p>",
                true
        );

        mailSender.send(message);
        System.out.println("✅ Email sent successfully to " + toEmail);
    }
}
