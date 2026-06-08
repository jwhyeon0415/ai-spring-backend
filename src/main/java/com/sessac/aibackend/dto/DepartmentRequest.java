package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Department;
import com.sessac.aibackend.domain.DeptType;
import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(
        @NotBlank String deptname
) {
    public Department toEntity() {
        return Department.builder()
                .deptname(deptname)
                .depttype(DeptType.LO)   // 처음에는 정규 부서로 고정.
                .build();
    }
}
