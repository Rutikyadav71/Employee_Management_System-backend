package com.rutik.ems.repository;

        import com.rutik.ems.model.Employee;
        import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
