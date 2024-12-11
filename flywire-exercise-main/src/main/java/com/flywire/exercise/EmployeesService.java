package com.flywire.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeesService {

    private List<Employee> employees;

    public EmployeesService(){
        ObjectMapper objectMapper = new ObjectMapper();
        employees = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/data.json")) {
            if (inputStream != null) {
                Employee[] data = objectMapper.readValue(inputStream, Employee[].class);
                Collections.addAll(employees, data);
            } else {
                System.err.println("Could not find 'data.json' in resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAll(){
        return employees;
    }

    public Optional<Employee> getId(int id){
        return employees.stream()
                .filter(employee -> employee.id == id)
                .findFirst();
    }

    public List<Employee> getEmployeeDateRange(String startDate, String endDate){
        return employees.stream()
                .filter(employee -> DateHelper.isDateBetween(employee.hireDate, startDate, endDate))
                .collect(Collectors.toList());
    }

    public String[] getDirectHireNames(Employee employee){
        List<String> dNames = new ArrayList<>();
        String[] directReportsNames = null;
        for(int dReport : employee.directReports){
            dNames.add(getEmployeeName(dReport));
            directReportsNames = dNames.toArray(new String[0]);
        }
        return directReportsNames;
    }

    public String getEmployeeName(int id){
        Optional<Employee> fEmployee = employees.stream()
                .filter(employee -> employee.id == id)
                .findFirst();
        return fEmployee.map(employee -> employee.name).orElse(null);
    }

    public Optional<Employee> newEmployee(NewEmployeeRequest nEmployee){
        if(!validateNewEmployeeRequest(nEmployee)){
            return Optional.empty();
        }
        employees.add(new Employee(nEmployee));
        SaveDataFile();
        if(nEmployee.manager != null){
            AddNewDirectReport(nEmployee.manager, nEmployee.id);
        }


        return getId(nEmployee.id);
    }

    private boolean validateNewEmployeeRequest(NewEmployeeRequest nEmployee){
        if(nEmployee.name.split(" ").length <= 1){
            System.out.println("Bad Name");
            return false;
        }
        if(!DateHelper.isValidDate(nEmployee.hireDate)){
            return false;
        }
        if(getId(nEmployee.id).isPresent()){
            System.out.println("ID Present");
            return false;
        }
        if(nEmployee.manager != null && !getId(nEmployee.manager).isPresent()){
            System.out.println("Manager not found");
            return false;
        }
        for(int report : nEmployee.directReports){
            if(!getId(report).isPresent()){
                System.out.println("Direct Report Not Found");
                return false;
            }
        }
        return true;
    }

    private void AddNewDirectReport(int managerId, int employeeId){
        Optional<Employee> manager = getId(managerId);
        int[] reports = manager.get().directReports;
        int[] newReports = Arrays.copyOf(reports, reports.length + 1);
        newReports[reports.length] = employeeId;
        manager.get().directReports = newReports;
    }

    public boolean deactivate(int id){
        Optional<Employee> fEmployee = employees.stream()
                .filter(employee -> employee.id == id)
                .findFirst();
        fEmployee.ifPresent(employee -> employee.active = false);
        SaveDataFile();
        return fEmployee.isPresent();
    }


    private void SaveDataFile(){
        File file = new File("src/main/resources/json/data.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, employees);
            System.out.println("Employees have been written to json/data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
