package com.rutik.ems.service;

import com.rutik.ems.model.Leave;
import com.rutik.ems.model.LeaveStatus;
import com.rutik.ems.repository.LeaveRepository;
import com.rutik.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository; // ✅ Add this

    public Leave applyLeave(Leave leave) {
        // ✅ Validate empId before saving leave
        boolean exists = employeeRepository.existsByEmpId(leave.getEmpId());
        if (!exists) {
            throw new RuntimeException("❌ Employee with empId " + leave.getEmpId() + " does not exist.");
        }

        leave.setStatus(LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }


    // Get all leave requests
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // Get leave requests by empId
    public List<Leave> getLeavesByEmpId(int empId) {
        return leaveRepository.findByEmpId(empId);
    }

    // Update leave status (APPROVED or REJECTED)
    public Leave updateLeaveStatus(Long leaveId, LeaveStatus status) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with ID: " + leaveId));
        leave.setStatus(status);
        return leaveRepository.save(leave);
    }

    // Delete leave request by ID
    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
