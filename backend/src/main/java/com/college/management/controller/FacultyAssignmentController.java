package com.college.management.controller;

import com.college.management.dto.FacultyCourseAssignmentRequest;
import com.college.management.entity.FacultyAssignedCourse;
import com.college.management.service.FacultyCourseAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty-assignments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FacultyAssignmentController {
    
    private final FacultyCourseAssignmentService assignmentService;
    
    @GetMapping("/faculty/{facultyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<List<FacultyAssignedCourse>> getFacultyAssignments(@PathVariable Long facultyId) {
        return ResponseEntity.ok(assignmentService.getFacultyAssignments(facultyId));
    }
    
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FacultyAssignedCourse>> getCourseAssignments(@PathVariable Long courseId) {
        return ResponseEntity.ok(assignmentService.getCourseAssignments(courseId));
    }
    
    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyAssignedCourse> assignCourse(@RequestBody FacultyCourseAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.assignCourse(request));
    }
    
    @DeleteMapping("/{assignmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long assignmentId) {
        assignmentService.removeAssignment(assignmentId);
        return ResponseEntity.ok().build();
    }
}
