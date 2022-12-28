package com.boev.project.controller;

import com.boev.project.service.ServiceResponse;
import com.boev.project.service.StudentWithCoursesService;
import com.boev.project.service.StudentWithCoursesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class StudentWithCourseServlet extends HttpServlet {

    private final StudentWithCoursesService service = new StudentWithCoursesServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int studentId = Integer.parseInt(req.getParameter("studentId"));

        int courseId = Integer.parseInt(req.getParameter("courseId"));

        service.updateStudentWithCourses(studentId, courseId);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        if (req.getParameter("id") == null) {

            List<ServiceResponse> responses = service.getAllStudentsWithCourses();

            responses.stream()
                    .filter(Objects::nonNull)
                    .forEach(serviceResponse -> {

                        printWriter.print("Student with courses: \n");
                        printWriter.print(serviceResponse.getStudent());

                        serviceResponse.getCourses().forEach(course -> printWriter.print(course + "\n"));

                    });

        } else {

            int id = Integer.parseInt(req.getParameter("id"));

            ServiceResponse serviceResponse = service.getStudentWithCourses(id);

            printWriter.print(serviceResponse.getStudent() + "is subscribed on courses:\n");

            serviceResponse.getCourses().stream()
                    .filter(Objects::nonNull)
                    .forEach(course -> printWriter.print(course + "\n"));

        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int studentId = Integer.parseInt(req.getParameter("studentId"));

        int courseId = Integer.parseInt(req.getParameter("courseId"));

        service.deleteCourseFromStudent(studentId, courseId);

    }

}
