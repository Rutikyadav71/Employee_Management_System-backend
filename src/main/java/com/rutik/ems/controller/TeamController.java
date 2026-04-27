package com.rutik.ems.controller;

import com.rutik.ems.model.Team;
import com.rutik.ems.repository.EmployeeRepository;
import com.rutik.ems.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

    @Autowired TeamRepository repo;
    @Autowired EmployeeRepository empRepo;

    /** Get ALL teams — accessible to any authenticated user (USER or ADMIN) */
    @GetMapping
    public List<Team> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable Long id) {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get teams for a specific employee.
     * Uses Java stream filter to avoid JPA ElementCollection query issues.
     * The Integer autoboxing / unboxing is handled explicitly.
     */
    @GetMapping("/employee/{empId}")
    public List<Team> getByEmployee(@PathVariable int empId) {
        return repo.findAll().stream()
            .filter(t -> {
                if (t.getMemberIds() == null || t.getMemberIds().isEmpty()) return false;
                // Explicit Integer comparison — avoid autoboxing ambiguity
                for (Integer id : t.getMemberIds()) {
                    if (id != null && id == empId) return true;
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    @PostMapping
    public Team create(@RequestBody Team team) {
        if (team.getCreatedDate() == null) team.setCreatedDate(LocalDate.now());
        return repo.save(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @RequestBody Team team) {
        return repo.findById(id).map(t -> {
            t.setName(team.getName());
            t.setDescription(team.getDescription());
            t.setProjectName(team.getProjectName());
            t.setDeadline(team.getDeadline());
            t.setStatus(team.getStatus());
            t.setMemberIds(team.getMemberIds() != null ? team.getMemberIds() : new ArrayList<>());
            t.setTeamLeadId(team.getTeamLeadId());
            return ResponseEntity.ok(repo.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/members/{empId}")
    public ResponseEntity<?> addMember(@PathVariable Long id, @PathVariable int empId) {
        return repo.findById(id).map(t -> {
            if (!t.getMemberIds().contains(empId)) {
                t.getMemberIds().add(empId);
            }
            return ResponseEntity.ok(repo.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/members/{empId}")
    public ResponseEntity<?> removeMember(@PathVariable Long id, @PathVariable int empId) {
        return repo.findById(id).map(t -> {
            t.getMemberIds().removeIf(mid -> mid != null && mid == empId);
            if (Objects.equals(t.getTeamLeadId(), empId)) t.setTeamLeadId(null);
            return ResponseEntity.ok(repo.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }
}
