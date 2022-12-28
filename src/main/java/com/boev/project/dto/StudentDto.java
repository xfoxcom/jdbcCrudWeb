package com.boev.project.dto;

import com.boev.project.entity.Student;

public class StudentDto {

    private int id;

    private String fullName;

    public StudentDto(Student student){
        this.id = student.getId();
        this.fullName = student.getName() + " " + student.getSurname();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Student #" + id + ": " + fullName + " with id " + id;
    }
}
