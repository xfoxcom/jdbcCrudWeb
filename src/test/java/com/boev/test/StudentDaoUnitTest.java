package com.boev.test;

import com.boev.project.dao.StudentDAO;
import com.boev.project.dao.daoImpl.StudentDaoImpl;
import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Student;
import com.boev.project.service.PropertiesLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StudentDaoUnitTest {

    private final Properties conf = PropertiesLoader.loadProperties();

    private final String USERNAME = conf.getProperty("user_name");

    private final String PASSWORD = conf.getProperty("user_password");

    private final String JDBC_DRIVER = conf.getProperty("jdbc_driver");

    private final String DATABASE_URL = conf.getProperty("database_url");

    StudentDAO studentDAO = new StudentDaoImpl();

    @BeforeEach
    public void init() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(
                    DATABASE_URL,
                    USERNAME,
                    PASSWORD);

            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS StudentCourseRelation");
            statement.execute("DROP TABLE IF EXISTS STUDENT");

            statement.execute("create table if not exists Student (id serial not NULL PRIMARY KEY, name varchar(15), surname VARCHAR(15)); ");

            statement.execute("INSERT INTO Student (name, surname) VALUES ('Alex', 'Boev');");

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void save_student_test() {

        Student student = new Student("John", "Doe");
        studentDAO.addStudent(student);

        List<StudentDto> students = studentDAO.getAllStudents();

        assertEquals(student.getName() + " " + student.getSurname(), students.get(1).getFullName());

    }

    @Test
    public void delete_student_test() {

        studentDAO.deleteStudent(1);

        List<StudentDto> students = studentDAO.getAllStudents();

        assertEquals(0, students.size());

    }

    @Test
    public void get_student_by_id_test() {

        StudentDto student = studentDAO.getById(1);

        assertEquals("Alex Boev", student.getFullName());

    }

}
