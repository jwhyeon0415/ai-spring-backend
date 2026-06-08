package com.sessac.aibackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor  // 의존성 주입을 final로 생성자 주입할 수 있.
public class SecurityConfig {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http  // http -> '.'으로 계속 연결 가능,,,
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)  // Session 꺼버리기,, (아래 애도 동일하게 꺼버리기)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 실패(401) + 권한 부족(403)을 모두 ErrorResponse JSON 표준 포맷으로 통일
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))   // 여기도 '.'으로 계속 에러 추가 가능,, (현재는 2개 에러)
                .authorizeHttpRequests(auth -> auth  // 'auth' 객체. 인증 부분에 Match,, -> 여기서는 Endpoint만 보는 중.
                        // 인증 불필요. /error는 403 에러 포워드가 막혀 401로 덮이지 않도록 개방
                        .requestMatchers(
                                "/login", "/signup", "/health",
                                "/oauth2/**", "/login/oauth2/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/h2-console/**", "/error"
                        ).permitAll()    // endpoint가 들어올 때의 정책은 뒤에다가 적어줌. -> p.19에 사용 가능 메서드 있,,,
                        // Day 2 인메모리 CRUD 학습용 (운영 권장 X)
                        .requestMatchers("/legacy/items/**").permitAll()    // 별도로 따로 한줄 추가해서 추가할 수도 있,,
                        // 관리자 전용
                        .requestMatchers("/admin/**").hasRole("ADMIN")      // 역할에 따라서 인가 처리,,
                        // 그 외 모두 인증 필요 (Day 3 JPA CRUD, /chat 등)
                        .anyRequest().authenticated()                         // 위의 요청들 제외한 모든 요청들은 이렇게 처리,,  (==>> endpoint별로 허용 어디까지 해주나 설정 가능)
                )
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2LoginSuccessHandler))
                // -> 분기 처리는 handler 쪽에서 (카카오/네이버/,,, 추가 원할 땐, yaml에 추가 & handler에 분기 추가,,)

                // H2 콘솔 사용을 위한 헤더 완화 (개발 프로파일만)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
