package com.college.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "academic_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String academicYear;
    
    @Column(nullable = false)
    private Integer semester;
    
    @Column(nullable = false)
    private Boolean active = true;
}
