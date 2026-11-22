package com.college.management.service;

import com.college.management.dto.CourseDto;
import com.college.management.entity.Course;
import com.college.management.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepository courseRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public List<Course> getActiveCourses() {
        return courseRepository.findByActive(true);
    }
    
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    
    @Transactional
    public Course createCourse(CourseDto courseDto) {
        if (courseRepository.existsByCourseCode(courseDto.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }
        
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setCourseCode(courseDto.getCourseCode());
        course.setCredits(courseDto.getCredits());
        course.setDescription(courseDto.getDescription());
        course.setActive(true);
        
        return courseRepository.save(course);
    }
    
    @Transactional
    public Course updateCourse(Long id, CourseDto courseDto) {
        Course course = getCourseById(id);
        
        if (!course.getCourseCode().equals(courseDto.getCourseCode()) && 
            courseRepository.existsByCourseCode(courseDto.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }
        
        course.setCourseName(courseDto.getCourseName());
        course.setCourseCode(courseDto.getCourseCode());
        course.setCredits(courseDto.getCredits());
        course.setDescription(courseDto.getDescription());
        course.setActive(courseDto.getActive());
        
        return courseRepository.save(course);
    }
    
    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        course.setActive(false);
        courseRepository.save(course);
    }
}
