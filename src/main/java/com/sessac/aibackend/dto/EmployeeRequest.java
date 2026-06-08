package com.sessac.aibackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeRequest(
        @NotNull Long deptId,
        @NotBlank String position
) {
}
