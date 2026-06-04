package com.sessac.aibackend.service;

import com.sessac.aibackend.util.CelebMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowingCelebService {

    private final CelebMatcher matcher;

    public String sport(String sport) { return matcher.match(sport); }
}
