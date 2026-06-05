package com.sessac.aibackend.controller;

import com.sessac.aibackend.domain.User;
import com.sessac.aibackend.dto.UserRequest;
import com.sessac.aibackend.dto.UserResponse;
import com.sessac.aibackend.error.NotFoundException;
import com.sessac.aibackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")  // Routing. endpoint 설정,, x -> 에러 발생 가능.
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;  // 의존성 주입,,

    @GetMapping
    public List<UserResponse> list() {
        return userService.findAll().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> NotFoundException.of("user", id));
        return UserResponse.from(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest req) {
        // username은 unique 제약이 있으므로, 충돌은 409로 명확히 응답합니다. -->> 이름 중복,,  (400번 대 주로 프론트엔드 관련)
        if (userService.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "username already exists: " + req.username());
        }
        User saved = userService.save(req.toEntity());
        URI location = URI.create("/users/" + saved.getId());
        return ResponseEntity.created(location).body(UserResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userService.existsById(id)) {      // Optional이 아니라 if문으로 throw하고 끝,,,
            throw NotFoundException.of("user", id);   // 240번..?
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
