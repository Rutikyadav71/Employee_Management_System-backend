package com.rutik.ems.service;

import com.rutik.ems.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(int empId);

    Employee create(Employee emp);

    Employee update(int empId, Employee emp);

    void delete(int empId);

}