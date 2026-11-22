package com.college.management.service;

import com.college.management.dto.AuthResponse;
import com.college.management.dto.LoginRequest;
import com.college.management.dto.RegisterRequest;
import com.college.management.entity.*;
import com.college.management.enums.Role;
import com.college.management.enums.UserStatus;
import com.college.management.repository.StudentProfileRepository;
import com.college.management.repository.UserRepository;
import com.college.management.repository.FacultyProfileRepository;
import com.college.management.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);
        
        user = userRepository.save(user);
        
        // Create profile based on role
        if (request.getRole() == Role.STUDENT) {
            if (studentProfileRepository.existsByRollNo(request.getRollNo())) {
                throw new RuntimeException("Roll number already exists");
            }
            
            StudentProfile studentProfile = new StudentProfile();
            studentProfile.setUser(user);
            studentProfile.setRollNo(request.getRollNo());
            studentProfile.setFirstName(request.getFirstName());
            studentProfile.setLastName(request.getLastName());
            studentProfile.setDepartment(request.getDepartment());
            studentProfile.setYear(request.getYear());
            studentProfile.setPhone(request.getPhone());
            studentProfile.setAddress(request.getAddress());
            
            studentProfileRepository.save(studentProfile);
        } else if (request.getRole() == Role.FACULTY) {
            if (facultyProfileRepository.existsByEmployeeId(request.getEmployeeId())) {
                throw new RuntimeException("Employee ID already exists");
            }
            
            FacultyProfile facultyProfile = new FacultyProfile();
            facultyProfile.setUser(user);
            facultyProfile.setEmployeeId(request.getEmployeeId());
            facultyProfile.setFirstName(request.getFirstName());
            facultyProfile.setLastName(request.getLastName());
            facultyProfile.setDepartment(request.getDepartment());
            facultyProfile.setPhone(request.getPhone());
            facultyProfile.setAddress(request.getAddress());
            facultyProfile.setDesignation(request.getDesignation());
            
            facultyProfileRepository.save(facultyProfile);
        }
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        
        return new AuthResponse(token, user.getEmail(), user.getRole(), user.getId(), "Registration successful");
    }
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Account is inactive");
        }
        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        
        return new AuthResponse(token, user.getEmail(), user.getRole(), user.getId(), "Login successful");
    }
}
