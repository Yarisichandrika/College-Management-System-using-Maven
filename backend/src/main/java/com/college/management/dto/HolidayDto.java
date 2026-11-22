package com.college.management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {
    
    private Long id;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotBlank(message = "Holiday name is required")
    private String holidayName;
    
    private String description;
}
