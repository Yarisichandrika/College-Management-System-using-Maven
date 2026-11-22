package com.college.management.repository;

import com.college.management.entity.FacultyAssignedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyAssignedCourseRepository extends JpaRepository<FacultyAssignedCourse, Long> {
    
    Optional<FacultyAssignedCourse> findByFacultyIdAndCourseId(Long facultyId, Long courseId);
    
    @Query("SELECT fac FROM FacultyAssignedCourse fac WHERE fac.faculty.id = :facultyId")
    List<FacultyAssignedCourse> findByFacultyId(@Param("facultyId") Long facultyId);
    
    @Query("SELECT fac FROM FacultyAssignedCourse fac WHERE fac.course.id = :courseId")
    List<FacultyAssignedCourse> findByCourseId(@Param("courseId") Long courseId);
}
