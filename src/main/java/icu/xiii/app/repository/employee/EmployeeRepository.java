package icu.xiii.app.repository.employee;

import icu.xiii.app.dto.employee.EmployeeDtoRequest;
import icu.xiii.app.entity.Employee;
import icu.xiii.app.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, EmployeeDtoRequest> {

    void save(EmployeeDtoRequest request);

    Optional<List<Employee>> getAll();

    Optional<Employee> getById(Long id);

    void update(Long id, EmployeeDtoRequest request);

    boolean deleteById(Long id);

    Optional<Employee> getLastEntity();
}
