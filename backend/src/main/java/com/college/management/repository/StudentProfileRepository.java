package com.college.management.repository;

import com.college.management.entity.StudentProfile;
import com.college.management.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    
    Optional<StudentProfile> findByRollNo(String rollNo);
    
    boolean existsByRollNo(String rollNo);
    
    @Query("SELECT s FROM StudentProfile s JOIN s.user u WHERE u.status = :status")
    List<StudentProfile> findByUserStatus(UserStatus status);
    
    @Query("SELECT s FROM StudentProfile s WHERE s.department = :department")
    List<StudentProfile> findByDepartment(String department);
    
    @Query("SELECT s FROM StudentProfile s WHERE s.year = :year")
    List<StudentProfile> findByYear(Integer year);
}
