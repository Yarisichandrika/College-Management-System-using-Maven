package com.college.management.dto;

import com.college.management.enums.UserStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    
    private Long id;
    
    private Long userId;
    
    @NotBlank(message = "Roll number is required")
    private String rollNo;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotNull(message = "Year is required")
    @Positive(message = "Year must be positive")
    private Integer year;
    
    private String email;
    
    private String phone;
    
    private String address;
    
    private String profilePicture;
    
    private UserStatus status;
}
