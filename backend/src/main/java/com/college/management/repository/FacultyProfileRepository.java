package com.college.management.repository;

import com.college.management.entity.FacultyProfile;
import com.college.management.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, Long> {
    
    Optional<FacultyProfile> findByEmployeeId(String employeeId);
    
    boolean existsByEmployeeId(String employeeId);
    
    @Query("SELECT f FROM FacultyProfile f JOIN f.user u WHERE u.status = :status")
    List<FacultyProfile> findByUserStatus(UserStatus status);
    
    @Query("SELECT f FROM FacultyProfile f WHERE f.department = :department")
    List<FacultyProfile> findByDepartment(String department);
}
