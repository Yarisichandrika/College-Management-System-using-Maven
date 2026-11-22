package com.college.management.service;

import com.college.management.dto.AttendanceMarkRequest;
import com.college.management.entity.*;
import com.college.management.enums.AttendanceStatus;
import com.college.management.exception.BusinessException;
import com.college.management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final StudentAttendanceRepository studentAttendanceRepository;
    private final FacultyAttendanceRepository facultyAttendanceRepository;
    private final StudentAssignedCourseRepository studentAssignedCourseRepository;
    private final FacultyAssignedCourseRepository facultyAssignedCourseRepository;
    private final HolidayRepository holidayRepository;
    
    @Transactional
    public void markStudentAttendance(Long facultyId, AttendanceMarkRequest request) {
        LocalDate date = request.getAttendanceDate();
        
        // Business Rule 1 & 2: Check if date is weekend or holiday
        if (isWeekend(date)) {
            throw new BusinessException("Attendance cannot be marked on weekends (Saturday/Sunday)");
        }
        
        if (isHoliday(date)) {
            throw new BusinessException("Attendance cannot be marked on holidays");
        }
        
        // Business Rule 3: Check if faculty is assigned to the course
        FacultyAssignedCourse facultyAssignment = facultyAssignedCourseRepository
                .findByFacultyIdAndCourseId(facultyId, request.getCourseId())
                .orElseThrow(() -> new BusinessException("Faculty is not assigned to this course"));
        
        // Mark attendance for each student
        for (Map.Entry<Long, AttendanceStatus> entry : request.getStudentAttendance().entrySet()) {
            Long studentId = entry.getKey();
            AttendanceStatus status = entry.getValue();
            
            // Business Rule 4: Check if student is enrolled in the course
            StudentAssignedCourse enrollment = studentAssignedCourseRepository
                    .findByStudentIdAndCourseId(studentId, request.getCourseId())
                    .orElseThrow(() -> new BusinessException("Student is not enrolled in this course"));
            
            // Business Rule 5: Check for duplicate attendance
            if (studentAttendanceRepository.findByStudentIdAndCourseIdAndAttendanceDate(
                    studentId, request.getCourseId(), date).isPresent()) {
                throw new BusinessException("Attendance already marked for this student on this date");
            }
            
            StudentAttendance attendance = new StudentAttendance();
            attendance.setStudent(enrollment.getStudent());
            attendance.setCourse(facultyAssignment.getCourse());
            attendance.setAttendanceDate(date);
            attendance.setStatus(status);
            attendance.setRemarks(request.getRemarks());
            
            studentAttendanceRepository.save(attendance);
        }
    }
    
    @Transactional
    public void markFacultyAttendance(Long facultyId, LocalDate date, AttendanceStatus status, String remarks) {
        // Business Rule 1 & 2: Check if date is weekend or holiday
        if (isWeekend(date)) {
            throw new BusinessException("Attendance cannot be marked on weekends (Saturday/Sunday)");
        }
        
        if (isHoliday(date)) {
            throw new BusinessException("Attendance cannot be marked on holidays");
        }
        
        // Check for duplicate attendance
        if (facultyAttendanceRepository.findByFacultyIdAndAttendanceDate(facultyId, date).isPresent()) {
            throw new BusinessException("Attendance already marked for this faculty on this date");
        }
        
        FacultyProfile faculty = new FacultyProfile();
        faculty.setId(facultyId);
        
        FacultyAttendance attendance = new FacultyAttendance();
        attendance.setFaculty(faculty);
        attendance.setAttendanceDate(date);
        attendance.setStatus(status);
        attendance.setRemarks(remarks);
        
        facultyAttendanceRepository.save(attendance);
    }
    
    public List<StudentAttendance> getStudentAttendance(Long studentId) {
        return studentAttendanceRepository.findByStudentId(studentId);
    }
    
    public List<StudentAttendance> getCourseAttendance(Long courseId) {
        return studentAttendanceRepository.findByCourseId(courseId);
    }
    
    public List<StudentAttendance> getAttendanceByDate(LocalDate date) {
        return studentAttendanceRepository.findByAttendanceDate(date);
    }
    
    public List<FacultyAttendance> getFacultyAttendance(Long facultyId) {
        return facultyAttendanceRepository.findByFacultyId(facultyId);
    }
    
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
    
    private boolean isHoliday(LocalDate date) {
        return holidayRepository.findByDate(date).isPresent();
    }
}
