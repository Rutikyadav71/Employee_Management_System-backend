package com.rutik.ems.controller;

import com.rutik.ems.model.Leave;
import com.rutik.ems.model.LeaveStatus;
import com.rutik.ems.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://ry-ems.vercel.app")
@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // Apply for leave (defaults to PENDING)
    @PostMapping
    public Leave applyLeave(@RequestBody Leave leave) {
        return leaveService.applyLeave(leave);
    }

    // Get all leave requests
    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    // Get leave history by empId (not DB id)
    @GetMapping("/employee/{empId}")
    public List<Leave> getLeavesByEmpId(@PathVariable("empId") int empId) {
        return leaveService.getLeavesByEmpId(empId);
    }

    // Update leave status (generic way)
    @PutMapping("/{id}")
    public Leave updateLeaveStatus(@PathVariable Long id, @RequestBody Leave leave) {
        return leaveService.updateLeaveStatus(id, leave.getStatus());
    }

    // Approve leave
    @PutMapping("/{id}/approve")
    public Leave approveLeave(@PathVariable Long id) {
        return leaveService.updateLeaveStatus(id, LeaveStatus.APPROVED);
    }

    // Reject leave
    @PutMapping("/{id}/reject")
    public Leave rejectLeave(@PathVariable Long id) {
        return leaveService.updateLeaveStatus(id, LeaveStatus.REJECTED);
    }

    // Delete a leave request
    @DeleteMapping("/{id}")
    public void deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
    }
}
