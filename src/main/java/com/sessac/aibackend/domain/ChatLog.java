package com.sessac.aibackend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity   // 영속 상태로 관리되는 객체,,
@Table(name = "chat_logs")   // 저 명칭의 table과 맵핑되겠군.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 여러 개
    @JoinColumn(name = "user_id", nullable = false)  // FK키 설정.
    private User user;       // 연관관계 주인.

    @Column(columnDefinition = "TEXT", nullable = false)
    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    @CreationTimestamp   // 생성 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;   // LocalDateTime 타입
}
