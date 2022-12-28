package com.boev.project.dao;

import com.boev.project.dto.CourseDto;
import com.boev.project.entity.Course;

import java.util.List;

public interface CourseDAO {

    CourseDto getById(int id);

    void addCourse(Course course);

    void deleteCourse(int id);

    List<CourseDto> getAllCourses();
}
