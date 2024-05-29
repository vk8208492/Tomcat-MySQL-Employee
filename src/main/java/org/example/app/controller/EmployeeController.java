package org.example.app.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.app.entity.Employee;
import org.example.app.repository.impl.EmployeeRepository;
import org.example.app.utils.Rounder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeController extends HttpServlet {

    private final EmployeeRepository repository = new EmployeeRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new" -> showNewForm(request, response);
                case "/insert" -> create(request, response);
                case "/delete" -> delete(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> update(request, response);
                default -> read(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String position = request.getParameter("position");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        double price = Rounder.round(Double.parseDouble(request.getParameter("price")), 2);
        Employee employee = new Employee(position, name, phone);
        repository.create(employee);
        response.sendRedirect("list");
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Employee> listBook = repository.read();
        request.setAttribute("listEmployee", listBook);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/book_list.jsp");
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String position = request.getParameter("position");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        double price = Rounder.round(Double.parseDouble(request.getParameter("price")), 2);
        Employee employee = new Employee(id, position, name, phone);
        repository.update(employee);
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        repository.delete(id);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/book_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Employee existingEmployee = repository.readById(id);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/book_form.jsp");
        request.setAttribute("employee", existingEmployee);
        dispatcher.forward(request, response);
    }
}
