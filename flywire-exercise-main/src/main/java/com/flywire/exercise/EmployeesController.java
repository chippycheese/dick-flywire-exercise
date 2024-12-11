package com.flywire.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/employee")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    //Create an http request endpoint that returns a list of all active employees in alphabetical order of last name.
    @GetMapping(value = "")
    public List<Employee> getAll() {
        List<Employee> employees = service.getAll();
        employees.sort(Comparator.comparing(Employee::getLastName));
        return employees;
    }

    //Create an http request endpoint that takes in an ID and returns a JSON response of the matching employees,
    // as well as the names of their direct hires. Employee IDs are unique.
    @GetMapping(value = "/{id}")
    public Employee getId(@PathVariable int id) {
        Optional<Employee> employee = service.getId(id);
        if(employee.isPresent()){
            return employee.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not found"
        );
    }

    //Create an http request endpoint that takes a date range, and returns a JSON response of all employees hired in
    //that date range. Sort by descending order of date hired.
    @GetMapping(value = "/date/{date1}/{date2}")
    public Employee[] getDateRange(@PathVariable String date1,@PathVariable String date2) {
        return null;
    }

    //Create an http request endpoint that takes a name, id, position, direct reports, and manager to creates a new
    //employee. The employee should be added to the JSON file. Add any validation and error handling you see fit.
    @PostMapping(value = "")
    public Employee getId(@RequestBody Employee nEmployee) {
        return null;
    }

    // Create an http request endpoint that takes in an ID and deactivates an employee. Add any validation you see fit.
    @DeleteMapping(value = "/{id}")
    public void deactivate(@PathVariable int id) {
        if(service.deactivate(id)){
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not found"
        );
    }

}
