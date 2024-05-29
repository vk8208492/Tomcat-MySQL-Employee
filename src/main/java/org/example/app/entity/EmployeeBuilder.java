package org.example.app.entity;

public class EmployeeBuilder {
    private String name;
    private String position;
    private String phone;

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder setPosition(String position) {
        this.position = position;
        return this;
    }

    public EmployeeBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Employee createEmployee() {
        return new Employee(name, position, phone);
    }
}