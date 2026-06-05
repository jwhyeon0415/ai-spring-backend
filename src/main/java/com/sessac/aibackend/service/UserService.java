package com.sessac.aibackend.service;

import com.sessac.aibackend.domain.User;
import com.sessac.aibackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 조회/저장 서비스.
 *
 * 순수 영속성 작업만 노출하고, "없으면 404" 같은 웹 계층 판단(NotFoundException)은
 * 컨트롤러에서 처리합니다. (ItemService와 동일한 책임 분리)
 */
@Service
@RequiredArgsConstructor   // 생성자 주입 가능 -> 의존성 받아 올 수 있,,
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional   // 조작 필요 _> readonly xx
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

// Transactional : 하나의 업무 단위로 rollback이 필요하다 -> annotation 달아줘,,
