package com.example.springExample.restapi;

import com.example.springExample.entities.Employee;
import com.example.springExample.entities.dtos.EmployeeDto;
import com.example.springExample.services.MessageResourceService;
import com.example.springExample.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/persons")
@RequiredArgsConstructor
public class EmployeeRestapi {
    private final EmployeeService employeeService;
    private final MessageResourceService messageResourceService;

    @GetMapping
    public ResponseEntity<Page<Employee>> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetail(@PathVariable long id) {
        Optional<Employee> optionalPerson = employeeService.findById(id);
        if (!optionalPerson.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return ResponseEntity.ok(optionalPerson.get());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody EmployeeDto employeeDto) {
        employeeService.create(employeeDto);
        return ResponseEntity.ok(messageResourceService.getMessage("create.success"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") long id, @RequestBody EmployeeDto employeeDto) {
        Optional<Employee> optionalPerson = employeeService.findById(id);
        if ((!optionalPerson.isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return ResponseEntity.ok(employeeService.update(employeeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Employee> optionalPerson = employeeService.findById(id);
        if ((!optionalPerson.isPresent())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        employeeService.delete(optionalPerson.get());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
