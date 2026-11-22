package com.college.management.service;

import com.college.management.dto.FacultyDto;
import com.college.management.entity.FacultyProfile;
import com.college.management.entity.User;
import com.college.management.enums.UserStatus;
import com.college.management.repository.FacultyProfileRepository;
import com.college.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {
    
    private final FacultyProfileRepository facultyProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<FacultyProfile> getAllFaculty() {
        return facultyProfileRepository.findAll();
    }
    
    public FacultyProfile getFacultyById(Long id) {
        return facultyProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
    }
    
    @Transactional
    public FacultyProfile createFaculty(FacultyDto facultyDto) {
        if (facultyProfileRepository.existsByEmployeeId(facultyDto.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists");
        }
        
        if (userRepository.existsByEmail(facultyDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Create user
        User user = new User();
        user.setEmail(facultyDto.getEmail());
        user.setPassword(passwordEncoder.encode("defaultPassword123"));
        user.setRole(com.college.management.enums.Role.FACULTY);
        user.setStatus(facultyDto.getStatus() != null ? facultyDto.getStatus() : UserStatus.ACTIVE);
        
        user = userRepository.save(user);
        
        // Create faculty profile
        FacultyProfile faculty = new FacultyProfile();
        faculty.setUser(user);
        faculty.setEmployeeId(facultyDto.getEmployeeId());
        faculty.setFirstName(facultyDto.getFirstName());
        faculty.setLastName(facultyDto.getLastName());
        faculty.setDepartment(facultyDto.getDepartment());
        faculty.setPhone(facultyDto.getPhone());
        faculty.setAddress(facultyDto.getAddress());
        faculty.setProfilePicture(facultyDto.getProfilePicture());
        faculty.setDesignation(facultyDto.getDesignation());
        
        return facultyProfileRepository.save(faculty);
    }
    
    @Transactional
    public FacultyProfile updateFaculty(Long id, FacultyDto facultyDto) {
        FacultyProfile faculty = getFacultyById(id);
        
        User user = faculty.getUser();
        if (!user.getEmail().equals(facultyDto.getEmail()) && 
            userRepository.existsByEmail(facultyDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        if (!faculty.getEmployeeId().equals(facultyDto.getEmployeeId()) && 
            facultyProfileRepository.existsByEmployeeId(facultyDto.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists");
        }
        
        user.setEmail(facultyDto.getEmail());
        user.setStatus(facultyDto.getStatus() != null ? facultyDto.getStatus() : user.getStatus());
        userRepository.save(user);
        
        faculty.setEmployeeId(facultyDto.getEmployeeId());
        faculty.setFirstName(facultyDto.getFirstName());
        faculty.setLastName(facultyDto.getLastName());
        faculty.setDepartment(facultyDto.getDepartment());
        faculty.setPhone(facultyDto.getPhone());
        faculty.setAddress(facultyDto.getAddress());
        faculty.setProfilePicture(facultyDto.getProfilePicture());
        faculty.setDesignation(facultyDto.getDesignation());
        
        return facultyProfileRepository.save(faculty);
    }
    
    @Transactional
    public void deactivateFaculty(Long id) {
        FacultyProfile faculty = getFacultyById(id);
        faculty.getUser().setStatus(UserStatus.INACTIVE);
        userRepository.save(faculty.getUser());
    }
    
    @Transactional
    public void activateFaculty(Long id) {
        FacultyProfile faculty = getFacultyById(id);
        faculty.getUser().setStatus(UserStatus.ACTIVE);
        userRepository.save(faculty.getUser());
    }
}
