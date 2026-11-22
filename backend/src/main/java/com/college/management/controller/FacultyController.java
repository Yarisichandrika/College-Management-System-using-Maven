package com.college.management.controller;

import com.college.management.entity.FacultyProfile;
import com.college.management.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('FACULTY')")
public class FacultyController {
    
    private final FacultyService facultyService;
    
    @GetMapping("/profile")
    public ResponseEntity<FacultyProfile> getFacultyProfile(Authentication authentication) {
        String email = authentication.getName();
        // In a real implementation, you would get the faculty ID from the token
        // For now, we'll return all faculty (this should be improved)
        return ResponseEntity.ok(facultyService.getAllFaculty().get(0));
    }
}
