package com.boev.project.dao;

import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Student;

import java.util.List;

public interface StudentDAO {

    StudentDto getById(int id);

    void addStudent(Student student);

    void deleteStudent(int id);

    List<StudentDto> getAllStudents();
}
