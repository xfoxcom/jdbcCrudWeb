package com.boev.project.controller;

import com.boev.project.dao.StudentDAO;
import com.boev.project.dao.daoImpl.StudentDaoImpl;
import com.boev.project.dto.StudentDto;
import com.boev.project.entity.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class StudentServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        if (req.getParameter("id") != null) {

            int id = Integer.parseInt(req.getParameter("id"));

            StudentDto student = studentDAO.getById(id);

            printWriter.print(student);


        } else {

            List<StudentDto> students = studentDAO.getAllStudents();

            students.stream()
                    .filter(Objects::nonNull)
                    .forEach(student -> printWriter.print(student + "\n"));

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        String surname = req.getParameter("surname");

        Student student = new Student(name, surname);

        studentDAO.addStudent(student);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        studentDAO.deleteStudent(id);

    }
}
