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
            EmployeeDtoGetByIdResponse dto =EmployeeDtoGetByIdResponse.of(employeeId, employee != null, employee);
            resp.setStatus(dto.statusCode());
            json = objectMapper.writeValueAsString(dto);
        } else if (pathValue != null && pathValue.equals("employees")) {
            List<Employee> employeeList = this.service.getAll();
            EmployeeDtoGetListResponse dto = EmployeeDtoGetListResponse.of(
                    employeeList.isEmpty(),
                    employeeList.isEmpty() ? Collections.emptyList() : employeeList
            );
            resp.setStatus(dto.statusCode());
            json = objectMapper.writeValueAsString(dto);
        }
        sendResponse(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (ServletInputStream in = req.getInputStream()) {
            EmployeeDtoRequest employeeDtoRequest = objectMapper.readValue(in, EmployeeDtoRequest.class);
            Employee employee = service.create(employeeDtoRequest);
            EmployeeDtoCreateResponse employeeDtoCreateResponse = EmployeeDtoCreateResponse.of(employee != null, employee);
            resp.setStatus(employeeDtoCreateResponse.statusCode());
            sendResponse(resp, employeeDtoCreateResponse);
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
                if (service.getById(employeeId) != null) {
                    employeeDtoUpdateResponse = EmployeeDtoUpdateResponse.of(
                            employeeId,
                            true,
                            service.update(employeeId, employeeDtoRequest)
                    );
                } else {
                    employeeDtoUpdateResponse = EmployeeDtoUpdateResponse.of(employeeId, false, null);
                }
                resp.setStatus(employeeDtoUpdateResponse.statusCode());
                sendResponse(resp, employeeDtoUpdateResponse);
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
            resp.setStatus(employeeDtoDeleteResponse.statusCode());
            sendResponse(resp, employeeDtoDeleteResponse);
        }
    }

    private void sendResponse(HttpServletResponse resp, Object dto) throws ServletException, IOException {
        sendResponse(resp, objectMapper.writeValueAsString(dto));
    }

    private void sendResponse(HttpServletResponse resp, String json) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        resp.setContentLength(json.length());
        try (ServletOutputStream out = resp.getOutputStream()) {
            out.println(json);
            out.flush();
        }
    }
}
