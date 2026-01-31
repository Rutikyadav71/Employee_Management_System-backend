package com.rutik.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class ChatUserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/api/chat-users")
    public List<Map<String, Object>> getAllChatUsers() {

        List<Map<String, Object>> users = new ArrayList<>();

        users.addAll(
                jdbcTemplate.queryForList(
                        "SELECT emp_id AS id, name, 'ROLE_USER' AS role FROM employees"
                )
        );

        users.addAll(
                jdbcTemplate.queryForList(
                        "SELECT id AS id, name, 'ROLE_ADMIN' AS role FROM admins"
                )
        );

        return users;
    }
}
