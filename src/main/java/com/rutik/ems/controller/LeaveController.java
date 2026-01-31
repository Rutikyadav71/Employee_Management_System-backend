package com.rutik.ems.controller;

import com.rutik.ems.model.Leave;
import com.rutik.ems.model.LeaveStatus;
import com.rutik.ems.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Leave applyLeave(@RequestBody Leave leave) {
        return leaveService.applyLeave(leave);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @PreAuthorize("hasRole('ADMIN') or @employeeSecurity.isSelf(#empId)")
    @GetMapping("/employee/{empId}")
    public List<Leave> getLeavesByEmpId(@PathVariable("empId") int empId) {
        return leaveService.getLeavesByEmpId(empId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Leave updateLeaveStatus(@PathVariable Long id, @RequestBody Leave leave) {
        return leaveService.updateLeaveStatus(id, leave.getStatus());
    }

    @PutMapping("/{id}/approve")
    public Leave approveLeave(@PathVariable Long id) {
        return leaveService.updateLeaveStatus(id, LeaveStatus.APPROVED);
    }

    @PutMapping("/{id}/reject")
    public Leave rejectLeave(@PathVariable Long id) {
        return leaveService.updateLeaveStatus(id, LeaveStatus.REJECTED);
    }

    @DeleteMapping("/{id}")
    public void deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
    }

    @GetMapping("/admin/export")
    public List<Leave> exportLeavesForMLTraining() {
        return leaveService.exportLeavesForTraining();
    }

    @GetMapping("/admin/{id}/prediction")
    public Map<String, Object> getLeaveMLInsight(@PathVariable Long id) {
        return leaveService.getLeaveMLInsight(id);
    }

    @GetMapping("/{id}")
    public Leave getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id);
    }

}
