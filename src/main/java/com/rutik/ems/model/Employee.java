package com.rutik.ems.model;
import jakarta.persistence.*;
@Entity
@Table(name = "employees")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    @Column(name = "emp_id", unique = true, nullable = false) private int empId;
    @Column(nullable = false) private String name;
    private String department;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String role;
    @Column(nullable = false) private long salary;
    @Column(name = "profile_image_url", length = 500) private String profileImageUrl;
    public int getId()              { return id; } public void setId(int id)       { this.id = id; }
    public int getEmpId()           { return empId; } public void setEmpId(int e)     { empId = e; }
    public String getName()         { return name; } public void setName(String n)   { name = n; }
    public String getDepartment()   { return department; } public void setDepartment(String d){ department = d; }
    public String getEmail()        { return email; } public void setEmail(String e)  { email = e; }
    public String getPassword()     { return password; } public void setPassword(String p){ password = p; }
    public String getRole()         { return role; } public void setRole(String r)   { role = r; }
    public long getSalary()         { return salary; } public void setSalary(long s)   { salary = s; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String url) { profileImageUrl = url; }
}