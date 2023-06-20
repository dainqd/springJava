package com.example.springExample.services;

import com.example.springExample.entities.Employee;
import com.example.springExample.entities.dtos.EmployeeDto;
import com.example.springExample.repositories.EmployeeRepository;
import com.example.springExample.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MessageResourceService messageResourceService;

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> findAllByStatus(Enums.EmployeeStatus status, Pageable pageable) {
        return employeeRepository.findAllByStatus(status, pageable);
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> findByIdAndStatus(long id, Enums.EmployeeStatus status) {
        return employeeRepository.findByIdAndStatus(id, status);
    }

    public Employee create(EmployeeDto employeeDto) {
        Employee employee = new Employee(employeeDto);
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setCreatedBy(1L);
        return employeeRepository.save(employee);
    }

    public Employee store(EmployeeDto employeeDto) {
        Employee employee = new Employee(employeeDto);
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setCreatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public Employee update(EmployeeDto employeeDto) {
        Optional<Employee> optionalPerson = employeeRepository.findById(employeeDto.getId());
        if (!optionalPerson.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("person.not.found"));
        }
        Employee employee = optionalPerson.get();

        BeanUtils.copyProperties(employeeDto, employee);

        employee.setUpdatedAt(LocalDateTime.now());
        employee.setUpdatedBy(1L);
        return employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employee.setStatus(Enums.EmployeeStatus.DELETED);
        employee.setDeletedAt(LocalDateTime.now());
        employee.setDeletedBy(1L);
        employeeRepository.save(employee);
    }
}
