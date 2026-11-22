package com.college.management.repository;

import com.college.management.entity.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicSessionRepository extends JpaRepository<AcademicSession, Long> {
    
    Optional<AcademicSession> findByAcademicYearAndSemester(String academicYear, Integer semester);
    
    List<AcademicSession> findByActive(Boolean active);
}
