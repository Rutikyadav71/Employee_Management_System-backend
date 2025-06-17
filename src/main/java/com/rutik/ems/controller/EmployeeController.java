package com.rutik.ems.controller;

        import com.rutik.ems.model.Employee;
        import com.rutik.ems.service.EmployeeService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://ry-employee-management-system.vercel.app")
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
    public Employee getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return service.create(emp);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee emp) {
        return service.update(id, emp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

