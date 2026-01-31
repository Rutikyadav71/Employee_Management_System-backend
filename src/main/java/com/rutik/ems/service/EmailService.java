package com.rutik.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.email.enabled}")
    private boolean emailEnabled;

    public void sendPasswordEmail(String toEmail, String name, String password) {

        if (!emailEnabled) {
            System.out.println("Email sending disabled for testing. Skipping email to: " + toEmail);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your EMS Account Credentials");
        message.setText(
                "Hello " + name + ",\n\n" +
                        "Your EMS account has been created.\n\n" +
                        "Email: " + toEmail + "\n" +
                        "Password: " + password + "\n\n" +
                        "Please change your password after logging in.\n\n" +
                        "Regards,\nEMS Team"
        );

        mailSender.send(message);
    }
}
