package com.sessac.aibackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 모든 요청에서 1회 실행되어 Authorization 헤더의 JWT를 검증합니다.
 *
 * 검증 성공 시 UserDetails 를 principal 로 세팅하여 컨트롤러에서
 * {@code @AuthenticationPrincipal UserDetails user} 로 받을 수 있도록 합니다.
 * (Form 로그인 경로와 principal 타입 일관성을 유지합니다.)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";  // header 속성값
    private static final String PREFIX = "Bearer ";    // 신원 인증 방법

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HEADER);          // Authorization으로. header 앞에 "Bearer" 토큰 있는지,,
        if (header != null && header.startsWith(PREFIX)) {
            String token = header.substring(PREFIX.length());
            try {
                Claims claims = jwtUtil.parse(token);
                String username = claims.getSubject();                                       // jwt 검증 성공했으니까 인증 객체에 담아야겠군 하는 애,,
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);   // Context Holder에 담기 위해,,    -> UserDetail 객체 생성, 토큰으로 만들어서 홀더에 넣어. (필터들이 그 인증..들 갖다 쓸거얄)

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                log.debug("JWT verification failed: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            } catch (UsernameNotFoundException e) {
                log.debug("user from JWT not found: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }
}
