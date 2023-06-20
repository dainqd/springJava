package com.example.springExample.repositories;

import com.example.springExample.entities.Employee;
import com.example.springExample.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findAllByStatus(Enums.EmployeeStatus status, Pageable pageable);

    Optional<Employee> findById(long id);

    Optional<Employee> findByIdAndStatus(long id, Enums.EmployeeStatus status);
}
