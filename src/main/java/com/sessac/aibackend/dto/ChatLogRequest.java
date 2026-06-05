package com.sessac.aibackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChatLogRequest(
        @NotNull Long userId,
        @NotBlank String prompt,
        String response
) {
}

// 이런 prompt 날렸더니, 이런 reponse 내보내고,,, 저장해야지....
