package com.college.management.controller;

import com.college.management.entity.StudentProfile;
import com.college.management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping("/profile")
    public ResponseEntity<StudentProfile> getStudentProfile(Authentication authentication) {
        String email = authentication.getName();
        // In a real implementation, you would get the student ID from the token
        // For now, we'll return all students (this should be improved)
        return ResponseEntity.ok(studentService.getAllStudents().get(0));
    }
}
