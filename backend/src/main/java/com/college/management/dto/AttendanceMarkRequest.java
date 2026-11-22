package com.college.management.dto;

import com.college.management.enums.AttendanceStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceMarkRequest {
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;
    
    @NotNull(message = "Student attendance records are required")
    private Map<Long, AttendanceStatus> studentAttendance;
    
    private String remarks;
}
