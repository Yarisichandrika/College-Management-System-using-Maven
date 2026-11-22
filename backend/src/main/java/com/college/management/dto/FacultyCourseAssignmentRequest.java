package com.college.management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyCourseAssignmentRequest {
    
    @NotNull(message = "Faculty ID is required")
    private Long facultyId;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
    
    @NotNull(message = "Semester is required")
    @Positive(message = "Semester must be positive")
    private Integer semester;
    
    @NotBlank(message = "Academic year is required")
    private String academicYear;
}
