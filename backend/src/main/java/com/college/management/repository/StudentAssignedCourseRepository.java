package com.college.management.repository;

import com.college.management.entity.StudentAssignedCourse;
import com.college.management.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAssignedCourseRepository extends JpaRepository<StudentAssignedCourse, Long> {
    
    Optional<StudentAssignedCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);
    
    @Query("SELECT sac FROM StudentAssignedCourse sac WHERE sac.student.id = :studentId AND sac.status = :status")
    List<StudentAssignedCourse> findByStudentIdAndStatus(@Param("studentId") Long studentId, 
                                                          @Param("status") EnrollmentStatus status);
    
    @Query("SELECT sac FROM StudentAssignedCourse sac WHERE sac.course.id = :courseId AND sac.status = :status")
    List<StudentAssignedCourse> findByCourseIdAndStatus(@Param("courseId") Long courseId, 
                                                         @Param("status") EnrollmentStatus status);
}
