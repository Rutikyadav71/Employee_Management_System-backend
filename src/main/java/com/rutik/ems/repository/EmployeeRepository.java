package com.rutik.ems.repository;
import com.rutik.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
        Optional<Employee> findByEmpId(int empId);
        void deleteByEmpId(int empId);

        boolean existsByEmpId(int empId);

        Optional<Employee> findByEmail(String email);

}
