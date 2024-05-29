package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.Employee;
import org.example.app.repository.AppRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeRepository implements AppRepository<Employee> {

    private static final Logger LOGGER =
            Logger.getLogger(EmployeeRepository.class.getName());

    public void create(Employee employee) {
        String sql = "INSERT INTO employee (name, phone, position ) VALUES (?, ?, ?)";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPhone());
            ps.setString(3, employee.getPosition());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public List<Employee> read() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConn.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Employee(
                        rs.getString("name"),
                                rs.getString("phone"),
                                rs.getString("position")
                        )
                );
            }
            // Повертаємо колекцію даних
            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            // Якщо помилка – повертаємо порожню колекцію
            return Collections.emptyList();
        }
    }

    public Employee readById(Long id) {
        Employee employee = null;
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee = new Employee(rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("position")
                );
            }
            ps.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return employee;
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET title = ?, author = ?, price = ? WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPosition());
            ps.setLong(4, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM employee where id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
