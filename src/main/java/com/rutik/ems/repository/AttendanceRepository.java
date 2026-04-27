package com.rutik.ems.repository;
import com.rutik.ems.model.Attendance;
import com.rutik.ems.model.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.*;
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByEmpId(int empId);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByEmpIdAndDateBetween(int empId, LocalDate start, LocalDate end);
    List<Attendance> findByDateBetween(LocalDate start, LocalDate end);
    Optional<Attendance> findByEmpIdAndDate(int empId, LocalDate date);
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.empId=:empId AND a.status=:status AND a.date BETWEEN :start AND :end")
    long countByStatusAndMonth(@Param("empId") int empId, @Param("status") AttendanceStatus status,
        @Param("start") LocalDate start, @Param("end") LocalDate end);
}