package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Employee;

public record EmployeeResponse(
        Long id,
        Long deptId,
        String deptName,
        String position
) {
    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getDepartment().getId(),
                null,
                employee.getPosition()
        );
    }

    public static EmployeeResponse fromWithDeptname(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getDepartment().getId(),
                employee.getDepartment().getDeptname(),
                employee.getPosition()
        );
    }
}
