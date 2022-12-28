package com.boev.project.service;

import com.boev.project.dto.CourseDto;
import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Course;
import com.boev.project.entity.Student;

import java.util.List;

public class ServiceResponse {
    private StudentDto student;
    private List<CourseDto> courses;

    public ServiceResponse() {
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }
}
