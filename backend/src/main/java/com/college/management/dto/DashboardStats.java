package com.college.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    
    private Long totalStudents;
    
    private Long totalFaculty;
    
    private Long totalCourses;
    
    private Long activeStudents;
    
    private Double averageAttendance;
}
