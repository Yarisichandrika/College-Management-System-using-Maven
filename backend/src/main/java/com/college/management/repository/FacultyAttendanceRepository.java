package com.college.management.repository;

import com.college.management.entity.FacultyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyAttendanceRepository extends JpaRepository<FacultyAttendance, Long> {
    
    Optional<FacultyAttendance> findByFacultyIdAndAttendanceDate(Long facultyId, LocalDate date);
    
    @Query("SELECT fa FROM FacultyAttendance fa WHERE fa.faculty.id = :facultyId")
    List<FacultyAttendance> findByFacultyId(@Param("facultyId") Long facultyId);
    
    @Query("SELECT fa FROM FacultyAttendance fa WHERE fa.attendanceDate = :date")
    List<FacultyAttendance> findByAttendanceDate(@Param("date") LocalDate date);
}
