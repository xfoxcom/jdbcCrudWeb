package com.boev.test;

import com.boev.project.dao.CourseDAO;
import com.boev.project.dao.StudentDAO;
import com.boev.project.dao.daoImpl.CourseDaoImpl;
import com.boev.project.dao.daoImpl.StudentDaoImpl;
import com.boev.project.dto.CourseDto;
import com.boev.project.entity.Course;
import com.boev.project.service.PropertiesLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDaoUnitTest {

    private final Properties conf = PropertiesLoader.loadProperties();

    private final String USERNAME = conf.getProperty("user_name");

    private final String PASSWORD = conf.getProperty("user_password");

    private final String JDBC_DRIVER = conf.getProperty("jdbc_driver");

    private final String DATABASE_URL = conf.getProperty("database_url");

    CourseDAO courseDAO = new CourseDaoImpl();

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
            statement.execute("DROP TABLE IF EXISTS COURSE");

            statement.execute("CREATE TABLE if not exists Course(" +
                    "id serial not NULL PRIMARY KEY," +
                    "courseName VARCHAR(100), price numeric);");

            statement.execute("INSERT INTO course (coursename, price) VALUES ('Java', 15);");

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void save_new_course() {

        Course course = new Course("Ruby", 7);

        courseDAO.addCourse(course);

        assertEquals(2, courseDAO.getAllCourses().size());
    }

    @Test
    public void get_course_by_id_test() {

        CourseDto course = courseDAO.getById(1);

        assertEquals("Java", course.getCourseName());
    }

    @Test
    public void delete_course_by_id_test() {

        courseDAO.deleteCourse(1);

        int size = courseDAO.getAllCourses().size();

        assertEquals(0, size);

    }
}
