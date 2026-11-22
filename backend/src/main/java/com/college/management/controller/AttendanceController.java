package com.college.management.controller;

import com.college.management.dto.AttendanceMarkRequest;
import com.college.management.entity.FacultyAttendance;
import com.college.management.entity.StudentAttendance;
import com.college.management.enums.AttendanceStatus;
import com.college.management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {
    
   @PostMapping("/mark-student")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Void> markStudentAttendance(Authentication authentication,
                                                       @RequestBody AttendanceMarkRequest request) {
        // In a real implementation, get faculty ID from authentication
        Long facultyId = 1L; // This should come from the authenticated user
        attendanceService.markStudentAttendance(facultyId, request);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/mark-faculty")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Void> markFacultyAttendance(Authentication authentication,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                      @RequestParam AttendanceStatus status,
                                                      @RequestParam(required = false) String remarks) {
        // In a real implementation, get faculty ID from authentication
        Long facultyId = 1L; // This should come from the authenticated user
        attendanceService.markFacultyAttendance(facultyId, date, status, remarks);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'FACULTY')")
    public ResponseEntity<List<StudentAttendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getStudentAttendance(studentId));
    }
    
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<List<StudentAttendance>> getCourseAttendance(@PathVariable Long courseId) {
        return ResponseEntity.ok(attendanceService.getCourseAttendance(courseId));
    }
    
    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentAttendance>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }
    
    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<List<FacultyAttendance>> getFacultyAttendance(@PathVariable Long facultyId) {
        return ResponseEntity.ok(attendanceService.getFacultyAttendance(facultyId));
    }
}
