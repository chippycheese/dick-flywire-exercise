package com.flywire.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public FindEmployeeResponse getId(@PathVariable int id) {
        Optional<Employee> employee = service.getId(id);
        if(employee.isPresent()){
            FindEmployeeResponse response = new FindEmployeeResponse(employee.get());
            response.directReportsNames = service.getDirectHireNames(employee.get());
            return response;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not found"
        );
    }

    //Create an http request endpoint that takes a date range, and returns a JSON response of all employees hired in
    //that date range. Sort by descending order of date hired.
    @PostMapping(value = "/date")
    public List<Employee> getDateRange(@RequestBody DateFindRequest request){
        if(!DateHelper.isValidDate(request.StartDate) || !DateHelper.isValidDate(request.EndDate) ){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad Request"
            );
        }
        List<Employee> employees = service.getEmployeeDateRange(request.StartDate, request.EndDate);
        Collections.sort(employees, new Comparator<Employee>() {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    @Override
                    public int compare(Employee e1, Employee e2) {
                        try {
                            return sdf.parse(e2.hireDate).compareTo(sdf.parse(e1.hireDate));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return 0; // Default in case of error
                        }
                    }
        });
        return employees;
    }

    //Create an http request endpoint that takes a name, id, position, direct reports, and manager to creates a new
    //employee. The employee should be added to the JSON file. Add any validation and error handling you see fit.
    @PostMapping(value = "")
    public Employee create(@RequestBody NewEmployeeRequest nEmployee) {
        Optional<Employee> employee = service.newEmployee(nEmployee);
        if(employee.isPresent()){
            return employee.get();
        }
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create"
        );
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
