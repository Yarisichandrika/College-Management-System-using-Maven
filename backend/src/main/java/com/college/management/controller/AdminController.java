package com.college.management.controller;

import com.college.management.dto.StudentDto;
import com.college.management.dto.FacultyDto;
import com.college.management.entity.StudentProfile;
import com.college.management.entity.FacultyProfile;
import com.college.management.service.StudentService;
import com.college.management.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final StudentService studentService;
    private final FacultyService facultyService;
    
    // Student Management
    @GetMapping("/students")
    public ResponseEntity<List<StudentProfile>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentProfile> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    
    @PostMapping("/students")
    public ResponseEntity<StudentProfile> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }
    
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentProfile> updateStudent(@PathVariable Long id, 
                                                         @Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }
    
    @PutMapping("/students/{id}/deactivate")
    public ResponseEntity<Void> deactivateStudent(@PathVariable Long id) {
        studentService.deactivateStudent(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/students/{id}/activate")
    public ResponseEntity<Void> activateStudent(@PathVariable Long id) {
        studentService.activateStudent(id);
        return ResponseEntity.ok().build();
    }
    
    // Faculty Management
    @GetMapping("/faculty")
    public ResponseEntity<List<FacultyProfile>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }
    
    @GetMapping("/faculty/{id}")
    public ResponseEntity<FacultyProfile> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }
    
    @PostMapping("/faculty")
    public ResponseEntity<FacultyProfile> createFaculty(@Valid @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(facultyService.createFaculty(facultyDto));
    }
    
    @PutMapping("/faculty/{id}")
    public ResponseEntity<FacultyProfile> updateFaculty(@PathVariable Long id, 
                                                          @Valid @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, facultyDto));
    }
    
    @PutMapping("/faculty/{id}/deactivate")
    public ResponseEntity<Void> deactivateFaculty(@PathVariable Long id) {
        facultyService.deactivateFaculty(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/faculty/{id}/activate")
    public ResponseEntity<Void> activateFaculty(@PathVariable Long id) {
        facultyService.activateFaculty(id);
        return ResponseEntity.ok().build();
    }
}
