package com.sessac.aibackend.repository;

import com.sessac.aibackend.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDeptIdOrderByIdDesc(Long deptId);

    @Query("""
           select e from Employee e
           join fetch e.department
           where e.department.id = :deptId
           order by e.id desc
           """)
    List<Employee> findByDeptIdWithDept(Long deptId);
}
