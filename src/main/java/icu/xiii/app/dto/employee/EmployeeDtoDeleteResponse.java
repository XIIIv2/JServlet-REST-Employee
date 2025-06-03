package icu.xiii.app.dto.employee;

import icu.xiii.app.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

public record EmployeeDtoDeleteResponse(
        int statusCode,
        boolean success,
        String message) {

    public static final String SUCCESS_MESSAGE = "Employee with id %s has been deleted successfully.";
    public static final String FAILURE_MESSAGE = "Employee with id %s has not been found!";

    public static EmployeeDtoDeleteResponse of(Long id, boolean isEmployeeDeleted) {
        if (isEmployeeDeleted) {
            return new EmployeeDtoDeleteResponse(
                    HttpServletResponse.SC_OK,
                    true,
                    SUCCESS_MESSAGE.formatted(id)
            );
        }
        return new EmployeeDtoDeleteResponse(
                HttpServletResponse.SC_NOT_FOUND,
                false,
                FAILURE_MESSAGE.formatted(id)
        );
    }
}
