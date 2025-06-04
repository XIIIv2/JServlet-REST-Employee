package icu.xiii.app.dto.employee;

import icu.xiii.app.dto.BaseDtoResponse;
import icu.xiii.app.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

public record EmployeeDtoUpdateResponse(
        int statusCode,
        boolean success,
        String message,
        Employee employee) implements BaseDtoResponse {

        public static final String SUCCESS_MESSAGE = "Employee with id %s has been updated successfully.";
        public static final String FAILURE_MESSAGE = "Employee with id %s has not been found!";

        public static EmployeeDtoUpdateResponse of(Long id, boolean isEmployeeUpdated, Employee employee) {
            if (isEmployeeUpdated)
                return new EmployeeDtoUpdateResponse(
                        HttpServletResponse.SC_OK,
                        true, SUCCESS_MESSAGE.formatted(id), employee);
            else
                return new EmployeeDtoUpdateResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        false, FAILURE_MESSAGE.formatted(id), null);
        }
}
