package com.sessac.aibackend.controller;


import com.sessac.aibackend.domain.Employee;
import com.sessac.aibackend.dto.EmployeeRequest;
import com.sessac.aibackend.dto.EmployeeResponse;
import com.sessac.aibackend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> list(@RequestParam Long deptId) {
        return employeeService.findByUserId(deptId).stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    @GetMapping("/with-dept")
    public List<EmployeeResponse> listWithUser(@RequestParam Long deptId) {
        return employeeService.findByUserIdWithUser(deptId).stream()
                .map(EmployeeResponse::fromWithDeptname)
                .toList();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest req) {
        Employee saved = employeeService.save(req.deptId(), req.position());
        URI location = URI.create("/emp-pos/" + saved.getId());
        return ResponseEntity.created(location).body(EmployeeResponse.from(saved));
    }
}
