package com.example.springExample.entities.dtos;

import com.example.springExample.entities.Employee;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    public long id;
    public String fullName;
    public String salary;
    public String status;

    public EmployeeDto(Employee employee) {
        BeanUtils.copyProperties(employee, this);
    }
}
