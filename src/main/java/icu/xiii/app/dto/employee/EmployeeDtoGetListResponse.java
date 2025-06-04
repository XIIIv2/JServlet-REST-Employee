package icu.xiii.app.dto.employee;

import icu.xiii.app.dto.BaseDtoResponse;
import icu.xiii.app.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

public record EmployeeDtoGetListResponse(
        int statusCode,
        boolean success,
        String message,
        List<Employee> employeeList) implements BaseDtoResponse {

    public static final String SUCCESS_MESSAGE = "Employee list has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Employee list has not been found!";

    public static EmployeeDtoGetListResponse of(boolean isUserListEmpty, List<Employee> employeeList) {
        if (isUserListEmpty)
            return new EmployeeDtoGetListResponse(
                    HttpServletResponse.SC_NOT_FOUND,
                    false, FAILURE_MESSAGE, Collections.emptyList());
        else
            return new EmployeeDtoGetListResponse(
                    HttpServletResponse.SC_OK,
                    true, SUCCESS_MESSAGE, employeeList);
    }
}
