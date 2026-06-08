package com.sessac.aibackend.service;


import com.sessac.aibackend.domain.Department;
import com.sessac.aibackend.domain.Employee;
import com.sessac.aibackend.error.NotFoundException;
import com.sessac.aibackend.repository.DepartmentRepository;
import com.sessac.aibackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(Long deptId, String position) {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> NotFoundException.of("department", deptId));
        return employeeRepository.save(
                Employee.builder()
                        .department(department)
                        .position(position)
                        .build()
        );
    }

        @Transactional(readOnly = true)
        public List<Employee> findByUserId(Long deptId) {
            departmentRepository.findById(deptId)
                    .orElseThrow(() -> NotFoundException.of("department", deptId));
            return employeeRepository.findByDeptIdOrderByCreatedAtDesc(deptId);
        }

        /**
         * fetch join 조회 — userId(PK)로 조회하며 user를 함께 로딩.
         * 응답에서 getUser().getUsername()을 읽는 fromWithUsername()과 짝을 이룹니다.
         */
        @Transactional(readOnly = true)
        public List<Employee> findByUserIdWithUser(Long deptId) {
            // 존재하지 않는 사용자는 404로 구분 (fetch join은 결과가 없으면 빈 리스트라 구분 불가)
            departmentRepository.findById(deptId)
                    .orElseThrow(() -> NotFoundException.of("department", deptId));
            return employeeRepository.findByDeptIdWithDept(deptId);
        }
    }
