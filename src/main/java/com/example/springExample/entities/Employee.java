package com.example.springExample.entities;

import com.example.springExample.entities.basic.BaseEntity;
import com.example.springExample.entities.dtos.EmployeeDto;
import com.example.springExample.utils.Enums;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "employees")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String fullName;
    @NotNull
    public String salary;
    @Enumerated(EnumType.STRING)
    public Enums.EmployeeStatus status = Enums.EmployeeStatus.INACTIVE;

    public Employee(EmployeeDto employeeDto) {
        BeanUtils.copyProperties(employeeDto, this);
    }
}
