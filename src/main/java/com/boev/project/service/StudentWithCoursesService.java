package com.boev.project.service;

import java.util.List;

public interface StudentWithCoursesService {

    ServiceResponse getStudentWithCourses(int studentId);

    List<ServiceResponse> getAllStudentsWithCourses();

    void updateStudentWithCourses(int studentId, int courseId);

    void deleteCourseFromStudent(int studentId, int courseId);

}
