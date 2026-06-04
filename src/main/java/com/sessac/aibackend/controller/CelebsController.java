package com.sessac.aibackend.controller;

import com.sessac.aibackend.service.ShowingCelebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("legacy/sports")
public class CelebsController {

    private final ShowingCelebService showingCelebService;

    @GetMapping("/celebs")
    public Map<String, String> celebs(
            @RequestParam(defaultValue = "스포츠 종목을 입력해주세요. (예: 축구)") String sport) {
        return Map.of("Celebrity Name", showingCelebService.sport(sport));
    }
}
