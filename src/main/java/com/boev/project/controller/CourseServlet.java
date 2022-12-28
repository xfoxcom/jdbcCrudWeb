package com.boev.project.controller;

import com.boev.project.dao.CourseDAO;
import com.boev.project.dao.daoImpl.CourseDaoImpl;
import com.boev.project.dto.CourseDto;
import com.boev.project.entity.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class CourseServlet extends HttpServlet {

    private final CourseDAO courseDAO = new CourseDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("coursename");

        double price = Double.parseDouble(req.getParameter("price"));

        Course course = new Course(name, price);

        courseDAO.addCourse(course);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        if (req.getParameter("id") != null) {

            int id = Integer.parseInt(req.getParameter("id"));

            CourseDto course = courseDAO.getById(id);

            printWriter.print(course);


        } else {

            List<CourseDto> courses = courseDAO.getAllCourses();

            courses.stream()
                    .filter(Objects::nonNull)
                    .forEach(course -> printWriter.print(course + "\n"));

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        courseDAO.deleteCourse(id);
    }
}
