package com.rutik.ems.repository;

import com.rutik.ems.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmpId(int empId);

}
