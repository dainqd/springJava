package com.example.springExample.controller;

import com.example.springExample.entities.dtos.EmployeeDto;
import com.example.springExample.services.EmployeeService;
import com.example.springExample.services.MessageResourceService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public String getList() {
        return "/v1/index";
    }

    @GetMapping("list")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<EmployeeDto> employeeDtos = employeeService.findAll(pageable).map(EmployeeDto::new);
            model.addAttribute("employeeDtos", employeeDtos);
            return "/v1/list";
        } catch (Exception e) {
            return "v1/fail";
        }
    }

    @GetMapping("create")
    public String createProcess(Model model) {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            model.addAttribute("employeeDto", employeeDto);
            return "/v1/create";
        } catch (Exception e) {
            return "v1/fail";
        }
    }

    @PostMapping("create/employee")
    public String create(@Valid @ModelAttribute EmployeeDto employeeDto,
                         Model model) {
        try {
            employeeService.create(employeeDto);
            model.addAttribute("success", "Create success!");
            return "redirect:/list";
        } catch (Exception e) {
            model.addAttribute("error", "Please try again!");
            return "v1/create";
        }
    }
}
