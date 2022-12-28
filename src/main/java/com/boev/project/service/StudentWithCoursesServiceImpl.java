package com.boev.project.service;

import com.boev.project.dao.CourseDAO;
import com.boev.project.dao.StudentDAO;
import com.boev.project.dao.daoImpl.CourseDaoImpl;
import com.boev.project.dao.daoImpl.StudentDaoImpl;
import com.boev.project.dto.CourseDto;
import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Course;
import com.boev.project.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class StudentWithCoursesServiceImpl implements StudentWithCoursesService {

    private final StudentDAO studentDAO = new StudentDaoImpl();

    private final CourseDAO courseDAO = new CourseDaoImpl();

    private final Properties conf = PropertiesLoader.loadProperties();

    private final String USERNAME = conf.getProperty("user_name");

    private final String PASSWORD = conf.getProperty("user_password");

    private final String JDBC_DRIVER = conf.getProperty("jdbc_driver");

    private final String DATABASE_URL = conf.getProperty("database_url");

    @Override
    public ServiceResponse getStudentWithCourses(int studentId) {

        ServiceResponse response = new ServiceResponse();

        response.setStudent(studentDAO.getById(studentId));

        List<CourseDto> courses = new ArrayList<>();

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

            PreparedStatement statement = connection.prepareStatement("SELECT COURSEID FROM StudentCourseRelation WHERE STUDENTID=?");

            statement.setInt(1, studentId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("courseid");

                CourseDto course = courseDAO.getById(id);
                course.setId(id);
                courses.add(course);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setCourses(courses);

        return response;
    }

    @Override
    public List<ServiceResponse> getAllStudentsWithCourses() {

        List<ServiceResponse> responses = new ArrayList<>();

        List<StudentDto> students = studentDAO.getAllStudents();

        Consumer<StudentDto> studentConsumer = student -> {

            ServiceResponse serviceResponse = new ServiceResponse();

            List<CourseDto> courses = new ArrayList<>();

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

                PreparedStatement statement = connection.prepareStatement("SELECT COURSEID FROM StudentCourseRelation WHERE STUDENTID=?");

                statement.setInt(1, student.getId());

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    int id = resultSet.getInt("courseid");

                    CourseDto course = courseDAO.getById(id);
                    course.setId(id);
                    courses.add(course);
                }

                serviceResponse.setStudent(student);
                serviceResponse.setCourses(courses);
                responses.add(serviceResponse);

                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        };

        students.forEach(studentConsumer);

        return responses;
    }

    @Override
    public void updateStudentWithCourses(int studentId, int courseId) {

        StudentDto student = studentDAO.getById(studentId);

        CourseDto course = courseDAO.getById(courseId);

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(
                    DATABASE_URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO StudentCourseRelation (STUDENTID, COURSEID) VALUES (?,?)");

            prepareStatement.setInt(1, student.getId());

            prepareStatement.setInt(2, course.getId());

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCourseFromStudent(int studentId, int courseId) {

        StudentDto student = studentDAO.getById(studentId);

        CourseDto course = courseDAO.getById(courseId);

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(
                    DATABASE_URL,
                    USERNAME,
                    PASSWORD
            );

            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM StudentCourseRelation WHERE STUDENTID=? AND COURSEID=?");

            prepareStatement.setInt(1, student.getId());

            prepareStatement.setInt(2, course.getId());

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
