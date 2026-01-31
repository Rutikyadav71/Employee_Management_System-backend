package com.rutik.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
    private String name;
    private String email;
    private Integer empId;

    // FOR ADMIN login
    public AuthResponse(String token, String role, String name, String email) {
        this.token = token;
        this.role = role;
        this.name = name;
        this.email = email;
        this.empId = null;
    }

    // FOR EMPLOYEE login
    public AuthResponse(String token, String role, String name, String email, Integer empId) {
        this.token = token;
        this.role = role;
        this.name = name;
        this.email = email;
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
