package com.rutik.ems.dto;

public class ChatUserDTO {

    private Integer empId;
    private String name;
    private String role;

    public ChatUserDTO(Integer empId, String name, String role) {
        this.empId = empId;
        this.name = name;
        this.role = role;
    }

    public Integer getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
