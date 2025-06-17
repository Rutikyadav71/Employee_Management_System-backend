package com.rutik.ems.controller;

import com.rutik.ems.model.Leave;
import com.rutik.ems.model.LeaveStatus;
import com.rutik.ems.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://ry-employee-management-system.vercel.app")
@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping
    public Leave applyLeave(@RequestBody Leave leave) {
        return leaveService.applyLeave(leave);
    }

    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/employee/{id}")
    public List<Leave> getLeavesByEmployee(@PathVariable Long id) {
        return leaveService.getLeavesByEmployeeId(id);
    }

    @PutMapping("/{id}")
    public Leave updateLeaveStatusGeneric(@PathVariable Long id, @RequestBody Leave leave) {
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
}
