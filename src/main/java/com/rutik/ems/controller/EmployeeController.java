package com.rutik.ems.controller;

import com.rutik.ems.model.Employee;
import com.rutik.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://ry-ems.vercel.app")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") int empId) {
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
}
