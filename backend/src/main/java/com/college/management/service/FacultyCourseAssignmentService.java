package com.college.management.service;

import com.college.management.dto.FacultyCourseAssignmentRequest;
import com.college.management.entity.Course;
import com.college.management.entity.FacultyAssignedCourse;
import com.college.management.entity.FacultyProfile;
import com.college.management.exception.BusinessException;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.FacultyAssignedCourseRepository;
import com.college.management.repository.FacultyProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyCourseAssignmentService {
    
    private final FacultyAssignedCourseRepository facultyAssignedCourseRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final CourseRepository courseRepository;
    
    public List<FacultyAssignedCourse> getFacultyAssignments(Long facultyId) {
        return facultyAssignedCourseRepository.findByFacultyId(facultyId);
    }
    
    public List<FacultyAssignedCourse> getCourseAssignments(Long courseId) {
        return facultyAssignedCourseRepository.findByCourseId(courseId);
    }
    
    @Transactional
    public FacultyAssignedCourse assignCourse(FacultyCourseAssignmentRequest request) {
        FacultyProfile faculty = facultyProfileRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new BusinessException("Faculty not found"));
        
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BusinessException("Course not found"));
        
        // Check if already assigned
        if (facultyAssignedCourseRepository.findByFacultyIdAndCourseId(
                request.getFacultyId(), request.getCourseId()).isPresent()) {
            throw new BusinessException("Faculty already assigned to this course");
        }
        
        FacultyAssignedCourse assignment = new FacultyAssignedCourse();
        assignment.setFaculty(faculty);
        assignment.setCourse(course);
        assignment.setSemester(request.getSemester());
        assignment.setAcademicYear(request.getAcademicYear());
        
        return facultyAssignedCourseRepository.save(assignment);
    }
    
    @Transactional
    public void removeAssignment(Long assignmentId) {
        facultyAssignedCourseRepository.deleteById(assignmentId);
    }
}
