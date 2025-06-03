package icu.xiii.app.service.employee;

import icu.xiii.app.dto.employee.EmployeeDtoRequest;
import icu.xiii.app.entity.Employee;
import icu.xiii.app.service.BaseService;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, EmployeeDtoRequest> {

    Employee create(EmployeeDtoRequest request);

    List<Employee> getAll();

    Employee getById(Long id);

    Employee update(Long id, EmployeeDtoRequest request);

    boolean deleteById(Long id);

}
