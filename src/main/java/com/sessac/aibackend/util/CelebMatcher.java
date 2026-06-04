package com.sessac.aibackend.util;

import java.util.Objects;

public class CelebMatcher {

    public String match(String sport) {
        return switch (sport) {
            case "축구" -> "손흥민";
            case "야구" -> "...";
            case "농구" -> "....";
            case null, default -> "종목을 다시 입력해주세요.";
        };

    }
}
