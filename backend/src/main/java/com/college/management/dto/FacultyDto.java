package com.college.management.dto;

import com.college.management.enums.UserStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto {
    
    private Long id;
    
    private Long userId;
    
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    private String email;
    
    private String phone;
    
    private String address;
    
    private String profilePicture;
    
    private String designation;
    
    private UserStatus status;
}
