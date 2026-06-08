package com.sessac.aibackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CORS 설정 (Day 5).
 *
 * React 개발 서버(Vite 기본 포트 5173)의 호출을 허용합니다.
 *
 * 보안 주의:
 * - allowCredentials=true 와 함께 allowedHeaders="*" 사용은 비권장입니다.
 *   필요한 헤더만 화이트리스트로 명시합니다.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(       // -> Port 뚫어주고,,
                "http://localhost:5173",
                "http://localhost:3000"        // FastAPI 포트
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));  // -> Port 설정,, (5173으로 들어온 얘네들은 Cors를 풀어준다..)
        config.setAllowedHeaders(List.of(                                                       // -> 보통 "GET", "POST", "PUT", "PATCH", "DELETE" 정도,, (기본적인 CRUD)
                "Authorization",
                "Content-Type",   // -> RAG할 때 필요,,?
                "Accept",
                "X-Requested-With"
        ));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);   // -> cors configuration source 이거 따른다,, 위의 설정한 모든 애들
        return source;
    }
}
