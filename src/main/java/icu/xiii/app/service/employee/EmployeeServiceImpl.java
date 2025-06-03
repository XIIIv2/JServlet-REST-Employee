package icu.xiii.app.service.employee;

import icu.xiii.app.dto.employee.EmployeeDtoRequest;
import icu.xiii.app.entity.Employee;
import icu.xiii.app.repository.employee.EmployeeRepository;
import icu.xiii.app.repository.employee.EmployeeRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository = new EmployeeRepositoryImpl();

    @Override
    public Employee create(EmployeeDtoRequest request) {
        Objects.requireNonNull(request, "Parameter [request] must not be null");
        repository.save(request);
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return repository.getAll()
                .orElse(Collections.emptyList());
    }

    @Override
    public Employee getById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null");
        return repository.getById(id)
                .orElse(null);
    }

    @Override
    public Employee update(Long id, EmployeeDtoRequest request) {
        Objects.requireNonNull(id,"Parameter [id] must not be null");
        Objects.requireNonNull(request, "Parameter [request] must not be null");
        if (repository.getById(id).isPresent()) {
            repository.update(id, request);
        }
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null");
        return repository.deleteById(id);
    }
}
