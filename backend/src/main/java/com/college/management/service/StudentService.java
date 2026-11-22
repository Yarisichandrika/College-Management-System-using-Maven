package com.college.management.service;

import com.college.management.dto.StudentDto;
import com.college.management.entity.StudentProfile;
import com.college.management.entity.User;
import com.college.management.enums.UserStatus;
import com.college.management.repository.StudentProfileRepository;
import com.college.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<StudentProfile> getAllStudents() {
        return studentProfileRepository.findAll();
    }
    
    public StudentProfile getStudentById(Long id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
    
    @Transactional
    public StudentProfile createStudent(StudentDto studentDto) {
        if (studentProfileRepository.existsByRollNo(studentDto.getRollNo())) {
            throw new RuntimeException("Roll number already exists");
        }
        
        if (userRepository.existsByEmail(studentDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Create user
        User user = new User();
        user.setEmail(studentDto.getEmail());
        user.setPassword(passwordEncoder.encode("defaultPassword123"));
        user.setRole(com.college.management.enums.Role.STUDENT);
        user.setStatus(studentDto.getStatus() != null ? studentDto.getStatus() : UserStatus.ACTIVE);
        
        user = userRepository.save(user);
        
        // Create student profile
        StudentProfile student = new StudentProfile();
        student.setUser(user);
        student.setRollNo(studentDto.getRollNo());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setDepartment(studentDto.getDepartment());
        student.setYear(studentDto.getYear());
        student.setPhone(studentDto.getPhone());
        student.setAddress(studentDto.getAddress());
        student.setProfilePicture(studentDto.getProfilePicture());
        
        return studentProfileRepository.save(student);
    }
    
    @Transactional
    public StudentProfile updateStudent(Long id, StudentDto studentDto) {
        StudentProfile student = getStudentById(id);
        
        User user = student.getUser();
        if (!user.getEmail().equals(studentDto.getEmail()) && 
            userRepository.existsByEmail(studentDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        if (!student.getRollNo().equals(studentDto.getRollNo()) && 
            studentProfileRepository.existsByRollNo(studentDto.getRollNo())) {
            throw new RuntimeException("Roll number already exists");
        }
        
        user.setEmail(studentDto.getEmail());
        user.setStatus(studentDto.getStatus() != null ? studentDto.getStatus() : user.getStatus());
        userRepository.save(user);
        
        student.setRollNo(studentDto.getRollNo());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setDepartment(studentDto.getDepartment());
        student.setYear(studentDto.getYear());
        student.setPhone(studentDto.getPhone());
        student.setAddress(studentDto.getAddress());
        student.setProfilePicture(studentDto.getProfilePicture());
        
        return studentProfileRepository.save(student);
    }
    
    @Transactional
    public void deactivateStudent(Long id) {
        StudentProfile student = getStudentById(id);
        student.getUser().setStatus(UserStatus.INACTIVE);
        userRepository.save(student.getUser());
    }
    
    @Transactional
    public void activateStudent(Long id) {
        StudentProfile student = getStudentById(id);
        student.getUser().setStatus(UserStatus.ACTIVE);
        userRepository.save(student.getUser());
    }
}
