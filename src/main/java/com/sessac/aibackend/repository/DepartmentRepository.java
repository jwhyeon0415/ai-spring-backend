package com.sessac.aibackend.repository;

import com.sessac.aibackend.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDeptname(String deptname);

    boolean existsByDeptname(String deptname);
}
