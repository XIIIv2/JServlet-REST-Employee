package icu.xiii.app.dto.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeDtoRequest(Long id, String name, String position, String phone) {
}
