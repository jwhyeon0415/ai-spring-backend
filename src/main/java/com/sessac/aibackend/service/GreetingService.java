package com.sessac.aibackend.service;

import com.sessac.aibackend.util.MessageFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GreetingService {

    private final MessageFormatter formatter;

    public String hello(String name) {
        return formatter.format(name);
    }
}
