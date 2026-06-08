package com.sessac.aibackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")   // 예약어 사용 위해
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 생성 전략,,
    private Long id;

    /**
     * 로그인 아이디 (유일)
     */
    @Column(
            unique = true,
            nullable = false,   // == NotNull
            length = 100
    )   // 제약 조건 추가.
    private String username;

    /**
     * BCrypt 해시 (Day4에서 사용)
     */
    @Column(length = 200)
    private String passwordHash;

    /**
     * USER / ADMIN
     */
    @Enumerated(EnumType.STRING)   // Enum 타입 처리 annotation -> enum 타입 안에 애들 문자열로 들어가겠군,,
    @Column(nullable = false, length = 20)
    private Role role;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String provider = "LOCAL";

    @Column(name = "provider_id", length = 255)
    private String providerId;

    public static User oauthUser(String email, String providerId) {
        return User.builder()
                .username(email)
                .passwordHash(null)
                .role(Role.USER)
                .provider("GOOGLE")
                .providerId(providerId)    // -> OAuth 성공 시 제공,,
                .build();
    }  // -> 패스워드 없고 provider 까지 담긴 user 객체 하나 생성됨 -> 저장,,, 시키는 객체 repository에 추가 (findByProviderAndProviderId)
}
