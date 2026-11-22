package com.college.management.service;

import com.college.management.dto.EnrollmentRequest;
import com.college.management.entity.Course;
import com.college.management.entity.StudentAssignedCourse;
import com.college.management.entity.StudentProfile;
import com.college.management.enums.EnrollmentStatus;
import com.college.management.exception.BusinessException;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.StudentAssignedCourseRepository;
import com.college.management.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    
    private final StudentAssignedCourseRepository studentAssignedCourseRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CourseRepository courseRepository;
    
    public List<StudentAssignedCourse> getStudentEnrollments(Long studentId) {
        return studentAssignedCourseRepository.findByStudentIdAndStatus(
            studentId, EnrollmentStatus.ENROLLED);
    }
    
    public List<StudentAssignedCourse> getCourseEnrollments(Long courseId) {
        return studentAssignedCourseRepository.findByCourseIdAndStatus(
            courseId, EnrollmentStatus.ENROLLED);
    }
    
    @Transactional
    public StudentAssignedCourse enrollStudent(EnrollmentRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.getStudentId())
                .orElseThrow(() -> new BusinessException("Student not found"));
        
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BusinessException("Course not found"));
        
        // Check if already enrolled
        if (studentAssignedCourseRepository.findByStudentIdAndCourseId(
                request.getStudentId(), request.getCourseId()).isPresent()) {
            throw new BusinessException("Student already enrolled in this course");
        }
        
        StudentAssignedCourse enrollment = new StudentAssignedCourse();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        
        return studentAssignedCourseRepository.save(enrollment);
    }
    
    @Transactional
    public void dropEnrollment(Long enrollmentId) {
        StudentAssignedCourse enrollment = studentAssignedCourseRepository.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException("Enrollment not found"));
        
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        studentAssignedCourseRepository.save(enrollment);
    }
}
