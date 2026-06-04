package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Sports;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SportsRequest(
        @NotBlank String sport,
        @Min(0) int num
) {
    public Sports toEntity() {
        return Sports.builder().sport(sport).num(num).build();
    }
}

