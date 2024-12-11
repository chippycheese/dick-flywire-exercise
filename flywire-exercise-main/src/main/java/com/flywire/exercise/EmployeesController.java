package com.flywire.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    @GetMapping(value = "")
    public Employee[] getAll() {
        return service.getAll();
    }

}
