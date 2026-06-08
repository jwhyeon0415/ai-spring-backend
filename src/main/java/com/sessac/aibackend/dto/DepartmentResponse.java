package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Department;
import com.sessac.aibackend.domain.DeptType;

public record DepartmentResponse(Long id, String deptname, DeptType deptType) {

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(department.getId(), department.getDeptname(), department.getDepttype());
    }
}
