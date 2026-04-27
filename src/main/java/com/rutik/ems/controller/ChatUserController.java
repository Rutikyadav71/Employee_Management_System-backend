package com.rutik.ems.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin(origins = "*")
public class ChatUserController {
    @Autowired JdbcTemplate jdbcTemplate;

    @GetMapping("/api/chat-users")
    public Map<String,Object> getAllChatUsers() {
        List<Map<String,Object>> employees = jdbcTemplate.queryForList(
            "SELECT emp_id AS id, name, email, department, profile_image_url AS profileImageUrl, " +
            "'USER' AS role FROM employees ORDER BY name ASC");
        List<Map<String,Object>> admins = jdbcTemplate.queryForList(
            "SELECT id, name, email, NULL AS department, NULL AS profileImageUrl, " +
            "'ADMIN' AS role FROM admins WHERE frozen=false OR frozen IS NULL ORDER BY name ASC");
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("employees", employees);
        result.put("admins",    admins);
        return result;
    }
}