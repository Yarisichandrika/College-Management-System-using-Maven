package com.college.management.service;

import com.college.management.dto.DashboardStats;
import com.college.management.enums.UserStatus;
import com.college.management.repository.*;
import com.college.management.enums.AttendanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final StudentProfileRepository studentProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final CourseRepository courseRepository;
    private final StudentAttendanceRepository studentAttendanceRepository;
    
    public DashboardStats getAdminDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        stats.setTotalStudents(studentProfileRepository.count());
        stats.setTotalFaculty(facultyProfileRepository.count());
        stats.setTotalCourses(courseRepository.count());
        stats.setActiveStudents(studentProfileRepository.findByUserStatus(UserStatus.ACTIVE).size());
        
        // Calculate average attendance
        List<com.college.management.entity.StudentAttendance> allAttendance = 
            studentAttendanceRepository.findAll();
        
        if (!allAttendance.isEmpty()) {
            long presentCount = allAttendance.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
            stats.setAverageAttendance((double) presentCount / allAttendance.size() * 100);
        } else {
            stats.setAverageAttendance(0.0);
        }
        
        return stats;
    }
}
