package com.sessac.aibackend.controller;

import com.sessac.aibackend.domain.Department;
import com.sessac.aibackend.dto.DepartmentRequest;
import com.sessac.aibackend.dto.DepartmentResponse;
import com.sessac.aibackend.error.NotFoundException;
import com.sessac.aibackend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/depts")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> list() {
        return departmentService.findAll().stream().map(DepartmentResponse::from).toList();
    }

    @GetMapping("/{id}")
    public DepartmentResponse get(@PathVariable Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> NotFoundException.of("department", id));
        return DepartmentResponse.from(department);
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest req) {
        // username은 unique 제약이 있으므로, 충돌은 409로 명확히 응답합니다.
        if (departmentService.existsByDeptname(req.deptname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "deptname already exists: " + req.deptname());
        }
        Department saved = departmentService.save(req.toEntity());
        URI location = URI.create("/users/" + saved.getId());
        return ResponseEntity.created(location).body(DepartmentResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!departmentService.existsById(id)) {
            throw NotFoundException.of("user", id);
        }
        departmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
