package com.boev.project.dao.daoImpl;

import com.boev.project.dao.CourseDAO;
import com.boev.project.dto.CourseDto;
import com.boev.project.entity.Course;
import com.boev.project.exception.CourseNotFoundException;
import com.boev.project.service.PropertiesLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CourseDaoImpl implements CourseDAO {

    private final Properties conf = PropertiesLoader.loadProperties();

    private final String USERNAME = conf.getProperty("user_name");

    private final String PASSWORD = conf.getProperty("user_password");

    private final String JDBC_DRIVER = conf.getProperty("jdbc_driver");

    private final String DATABASE_URL = conf.getProperty("database_url");

    @Override
    public CourseDto getById(int id) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("SELECT COURSENAME,PRICE FROM COURSE WHERE ID=?");

            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString("coursename");

                double price = resultSet.getDouble("price");

                CourseDto course = new CourseDto(new Course(name, price));
                course.setId(id);

                return course;

            }

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new CourseNotFoundException("No course with id = " + id);
    }

    @Override
    public void addCourse(Course course) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO COURSE (COURSENAME, PRICE) VALUES (?,?)");

            prepareStatement.setString(1, course.getCourseName());

            prepareStatement.setDouble(2, course.getPrice());

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCourse(int id) {

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

            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM COURSE WHERE ID=?");

            prepareStatement.setInt(1, id);

            prepareStatement.executeUpdate();

            prepareStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<CourseDto> getAllCourses() {

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

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT ID,COURSENAME,PRICE FROM COURSE");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("coursename");
                double price = resultSet.getDouble("price");
                Course course = new Course(name, price);
                course.setId(id);
                courses.add(new CourseDto(course));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
