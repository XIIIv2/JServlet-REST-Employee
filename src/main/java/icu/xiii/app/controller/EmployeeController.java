package icu.xiii.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import icu.xiii.app.dto.employee.*;
import icu.xiii.app.entity.Employee;
import icu.xiii.app.service.employee.EmployeeService;
import icu.xiii.app.service.employee.EmployeeServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "Employees", urlPatterns = "/api/v1/employees/*")
public class EmployeeController extends HttpServlet {

    private EmployeeService service;
    private ObjectMapper objectMapper;
    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void init(ServletConfig config) throws ServletException {
        service = new EmployeeServiceImpl();
        objectMapper = new ObjectMapper();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        String json = "";

        if (pathValue != null && !pathValue.isBlank() && !pathValue.equals("employees")) {
            Long employeeId = Long.parseLong(pathValue);
            Employee employee = this.service.getById(employeeId);
            EmployeeDtoGetByIdResponse dto;
            if (employee != null) {
                dto = EmployeeDtoGetByIdResponse.of(employeeId, true, employee);
            } else {
                dto = EmployeeDtoGetByIdResponse.of(employeeId, false, null);
            }
            resp.setStatus(dto.statusCode());
            json = objectMapper.writeValueAsString(dto);
        } else if (pathValue != null && pathValue.equals("employees")) {
            List<Employee> employeeList = this.service.getAll();
            EmployeeDtoGetListResponse dto;
            if (employeeList.isEmpty()) {
                dto = EmployeeDtoGetListResponse.of(true, Collections.emptyList());
            } else {
                dto = EmployeeDtoGetListResponse.of(false, employeeList);
            }
            resp.setStatus(dto.statusCode());
            json = objectMapper.writeValueAsString(dto);
        }

        try(ServletOutputStream out = resp.getOutputStream()) {
            resp.setContentType(CONTENT_TYPE);
            resp.setCharacterEncoding(CHARACTER_ENCODING);
            resp.setContentLength(json.length());
            out.println(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (ServletInputStream in = req.getInputStream()) {
            EmployeeDtoRequest employeeDtoRequest = objectMapper.readValue(in, EmployeeDtoRequest.class);
            Employee employee = service.create(employeeDtoRequest);
            EmployeeDtoCreateResponse employeeDtoCreateResponse;

            if (employee != null) {
                employeeDtoCreateResponse = EmployeeDtoCreateResponse.of(true, employee);
            } else {
                employeeDtoCreateResponse = EmployeeDtoCreateResponse.of(false, null);
            }
            try (ServletOutputStream out = resp.getOutputStream()) {
                String json = objectMapper.writeValueAsString(employeeDtoCreateResponse);
                resp.setContentType(CONTENT_TYPE);
                resp.setCharacterEncoding(CHARACTER_ENCODING);
                resp.setContentLength(json.length());
                resp.setStatus(employeeDtoCreateResponse.statusCode());
                out.println(json);
                out.flush();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isBlank()) {
            try (ServletInputStream in = req.getInputStream()) {
                Long employeeId = Long.parseLong(pathValue);
                EmployeeDtoRequest employeeDtoRequest = objectMapper.readValue(in, EmployeeDtoRequest.class);
                EmployeeDtoUpdateResponse employeeDtoUpdateResponse;
                Employee employee = service.getById(employeeId);
                if (employee != null) {
                    employee = service.update(employeeId, employeeDtoRequest);
                    employeeDtoUpdateResponse = EmployeeDtoUpdateResponse.of(employeeId, true, employee);
                } else {
                    employeeDtoUpdateResponse = EmployeeDtoUpdateResponse.of(employeeId, false, null);
                }
                try (ServletOutputStream out = resp.getOutputStream()) {
                    String json = objectMapper.writeValueAsString(employeeDtoUpdateResponse);
                    resp.setContentType(CONTENT_TYPE);
                    resp.setCharacterEncoding(CHARACTER_ENCODING);
                    resp.setStatus(employeeDtoUpdateResponse.statusCode());
                    resp.setContentLength(json.length());
                    out.println(json);
                    out.flush();
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isBlank()) {
            Long employeeId = Long.parseLong(pathValue);
            boolean isEmployeeDeleted = service.deleteById(employeeId);
            EmployeeDtoDeleteResponse employeeDtoDeleteResponse = EmployeeDtoDeleteResponse.of(employeeId, isEmployeeDeleted);
            try (ServletOutputStream out = resp.getOutputStream()) {
                String json = objectMapper.writeValueAsString(employeeDtoDeleteResponse);
                resp.setContentType(CONTENT_TYPE);
                resp.setCharacterEncoding(CHARACTER_ENCODING);
                resp.setStatus(employeeDtoDeleteResponse.statusCode());
                resp.setContentLength(json.length());
                out.println(json);
                out.flush();
            }
        }
    }
}
