package com.sessac.aibackend.service;

import com.sessac.aibackend.domain.Department;
import com.sessac.aibackend.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public boolean findByDeptname(String deptname) {
        return departmentRepository.existsByDeptname(deptname);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByDeptname(String deptname) {
        return departmentRepository.existsByDeptname(deptname);
    }

    @Transactional
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

}
