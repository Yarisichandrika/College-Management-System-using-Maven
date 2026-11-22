package com.college.management.repository;

import com.college.management.entity.StudentAttendance;
import com.college.management.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
    
    Optional<StudentAttendance> findByStudentIdAndCourseIdAndAttendanceDate(Long studentId, Long courseId, LocalDate date);
    
    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.student.id = :studentId AND sa.course.id = :courseId")
    List<StudentAttendance> findByStudentIdAndCourseId(@Param("studentId") Long studentId, 
                                                        @Param("courseId") Long courseId);
    
    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.student.id = :studentId")
    List<StudentAttendance> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.course.id = :courseId")
    List<StudentAttendance> findByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.attendanceDate = :date")
    List<StudentAttendance> findByAttendanceDate(@Param("date") LocalDate date);
}
