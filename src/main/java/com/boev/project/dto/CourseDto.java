package com.boev.project.dto;

import com.boev.project.entity.Course;

public class CourseDto {

    private int id;

    private String courseName;

    public CourseDto(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Course #" + id + ": " + courseName;
    }
}
