package com.sessac.aibackend.config;

import com.sessac.aibackend.util.CelebMatcher;
import com.sessac.aibackend.util.MessageFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MessageFormatter messageFormatter() {
        return new MessageFormatter();
    }

    @Bean
    public CelebMatcher celebMatcher() {
        return new CelebMatcher();
    }
}
