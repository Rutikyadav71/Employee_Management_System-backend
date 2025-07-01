package com.rutik.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordEmail(String toEmail, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your EMS Account Credentials");
        message.setText("Hello " + name + ",\n\nYour EMS account has been created.\n\n" +
                "Email: " + toEmail + "\n" +
                "Password: " + password + "\n\n" +
                "Please change your password after logging in.\n\nRegards,\nEMS Team");

        mailSender.send(message);
    }
}
