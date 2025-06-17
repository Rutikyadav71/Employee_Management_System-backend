package com.rutik.ems.service;

        import com.rutik.ems.model.Employee;
        import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    Employee getById(int id);
    Employee create(Employee emp);
    Employee update(int id, Employee emp);
    void delete(int id);
}
