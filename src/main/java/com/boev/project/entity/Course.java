package com.boev.project.entity;

import java.util.Objects;

public class Course {

    private int id;

    private String courseName;

    private double price;

    public Course(String courseName, double price) {
        this.courseName = courseName;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course with " +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", price=" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Double.compare(course.getPrice(), getPrice()) == 0 && Objects.equals(getCourseName(), course.getCourseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseName(), getPrice());
    }
}
