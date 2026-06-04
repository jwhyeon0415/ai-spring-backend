package com.sessac.aibackend.dto;

import com.sessac.aibackend.domain.Sports;

public record SportsResponse(Long id, String sport, int num) {

    public static SportsResponse from(Sports sports) {
        return new SportsResponse(sports.getId(), sports.getSport(), sports.getNum());
    }
}
