package com.rutik.ems.service;

import com.rutik.ems.model.Leave;
import com.rutik.ems.model.LeaveStatus;
import com.rutik.ems.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    // Apply for leave (always PENDING by default)
    public Leave applyLeave(Leave leave) {
        leave.setStatus(LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }

    // Get all leave requests
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // Get leave requests by employee ID
    public List<Leave> getLeavesByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
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
