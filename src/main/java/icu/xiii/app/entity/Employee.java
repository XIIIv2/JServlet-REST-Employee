package icu.xiii.app.entity;

import icu.xiii.app.dto.employee.EmployeeDtoRequest;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "phone", nullable = false)
    private String phone;

    public Employee() {

    }

    public Employee(String name, String position, String phone) {
        this();
        this.name = name;
        this.position = position;
        this.phone = phone;
    }

    public Employee(EmployeeDtoRequest request) {
        this.id = request.id();
        this.name = request.name();
        this.position = request.position();
        this.phone = request.phone();
    }

    public Employee(Long id, EmployeeDtoRequest request) {
        this(request);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && name.equals(employee.name) && position.equals(employee.position) && phone.equals(employee.phone);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
