package com.boev.project.dao.daoImpl;

import com.boev.project.dao.StudentDAO;
import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Student;
import com.boev.project.exception.StudentNotFoundException;
import com.boev.project.service.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentDaoImpl implements StudentDAO {

    private final Properties conf = PropertiesLoader.loadProperties();

    private final String USERNAME = conf.getProperty("user_name");

    private final String PASSWORD = conf.getProperty("user_password");

    private final String JDBC_DRIVER = conf.getProperty("jdbc_driver");

    private final String DATABASE_URL = conf.getProperty("database_url");

    @Override
    public StudentDto getById(int id) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("SELECT NAME,SURNAME FROM STUDENT WHERE ID=?");

            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString("name");

                String surname = resultSet.getString("surname");

                Student student = new Student(name, surname);

                student.setId(id);

                StudentDto studentDto = new StudentDto(student);

                return studentDto;

            }

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new StudentNotFoundException("No student with id = " + id);
    }

    @Override
    public void addStudent(Student student) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO STUDENT (NAME, SURNAME) VALUES (?,?)");

            prepareStatement.setString(1, student.getName());

            prepareStatement.setString(2, student.getSurname());

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteStudent(int id) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM STUDENT WHERE ID=?");

            prepareStatement.setInt(1, id);

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<StudentDto> getAllStudents() {

        List<StudentDto> students = new ArrayList<>();

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

            ResultSet resultSet = statement.executeQuery("SELECT ID,NAME,SURNAME FROM STUDENT");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Student student = new Student(name, surname);
                student.setId(id);
                students.add(new StudentDto(student));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
