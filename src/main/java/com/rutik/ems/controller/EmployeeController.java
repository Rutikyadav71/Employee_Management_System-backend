package com.rutik.ems.controller;

import com.rutik.ems.model.Employee;
import com.rutik.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @employeeSecurity.isSelf(#empId)")
    @GetMapping("/{empId}")
    public Employee getById(@PathVariable int empId) {
        return service.getById(empId);
    }


    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return service.create(emp);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable("id") int empId, @RequestBody Employee emp) {
        return service.update(empId, emp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int empId) {
        service.delete(empId);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return service.searchEmployees(keyword);
    }

    @PostMapping("/delete-multiple")
    public void deleteMultiple(@RequestBody List<Integer> empIds) {
        service.deleteMultiple(empIds);
    }
}
