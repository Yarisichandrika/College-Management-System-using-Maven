package com.college.management.dto;

import com.college.management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    
    private String email;
    
    private Role role;
    
    private Long userId;
    
    private String message;
}
