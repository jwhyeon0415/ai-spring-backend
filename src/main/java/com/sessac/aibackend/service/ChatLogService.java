package com.sessac.aibackend.service;

import com.sessac.aibackend.domain.ChatLog;
import com.sessac.aibackend.domain.User;
import com.sessac.aibackend.error.NotFoundException;
import com.sessac.aibackend.repository.ChatLogRepository;
import com.sessac.aibackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 채팅 로그 저장/조회 서비스.
 *
 * 트랜잭션 경계를 서비스 메서드 단위로 명시합니다.
 * 비동기 흐름(Mono)에서 호출되더라도 이 메서드는 동기 트랜잭션에서 실행됩니다.
 */
@Service
@RequiredArgsConstructor
public class ChatLogService {

    // 두 개 의존성 받아와 -> user, chatlog 두개 모두 접근 가능,,
    private final UserRepository userRepository;
    private final ChatLogRepository chatLogRepository;

    @Transactional
    public ChatLog save(Long userId, String prompt, String response) {  // User 객체를 갖고 있기 때문에, 부모 한번 조회해서,, (부모를 알고 부모를 품어야 함)
        User user = userRepository.findById(userId)   // 객체와 객체의 영속된 상태로 써야 해 ((DB가)부모 아이디랑 매칭된 상태에서 저장)
                .orElseThrow(() -> NotFoundException.of("user", userId));
        return chatLogRepository.save(
                ChatLog.builder()
                        .user(user)
                        .prompt(prompt)
                        .response(response)
                        .build()
        );
    }

    /**
     * 기본 조회 — userId(PK)로 조회, fetch join 없음.
     * 응답에서 getUser().getId()만 읽는 from()과 짝을 이룹니다.
     */
    @Transactional(readOnly = true)
    public List<ChatLog> findByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("user", userId));
        return chatLogRepository.findByUserIdOrderByCreatedAtDesc(userId);  // 쿼리메시지 -> 기본 제공 x.
    }

    /**
     * fetch join 조회 — userId(PK)로 조회하며 user를 함께 로딩.
     * 응답에서 getUser().getUsername()을 읽는 fromWithUsername()과 짝을 이룹니다.
     */
    @Transactional(readOnly = true)
    public List<ChatLog> findByUserIdWithUser(Long userId) {
        // 존재하지 않는 사용자는 404로 구분 (fetch join은 결과가 없으면 빈 리스트라 구분 불가)
        userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("user", userId));
        return chatLogRepository.findByUserIdWithUser(userId);
    }
}
