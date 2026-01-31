package com.rutik.ems.repository;

import com.rutik.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

        Optional<Employee> findByEmpId(int empId);
        void deleteByEmpId(int empId);
        boolean existsByEmpId(int empId);
        Optional<Employee> findByEmail(String email);

        @Query("""
       SELECT e FROM Employee e
       WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(e.role) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
        List<Employee> searchEmployees(@Param("keyword") String keyword);

        @Modifying
        @Transactional
        void deleteAllByEmpIdIn(List<Integer> empIds);
}
