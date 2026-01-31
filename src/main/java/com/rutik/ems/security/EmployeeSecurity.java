package com.rutik.ems.security;

import com.rutik.ems.repository.EmployeeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSecurity {

    private final EmployeeRepository employeeRepository;

    public EmployeeSecurity(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean isSelf(int empId) {

        // principal = email (from JwtFilter)
        String loggedInEmail =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return employeeRepository
                .findByEmpId(empId) // business ID
                .map(emp -> emp.getEmail().equalsIgnoreCase(loggedInEmail))
                .orElse(false);
    }
}
