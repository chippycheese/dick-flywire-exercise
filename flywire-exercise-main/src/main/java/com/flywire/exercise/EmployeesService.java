package com.flywire.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional<Employee> fEmployee = employees.stream()
                .filter(employee -> employee.id == id)
                .findFirst();
        if(fEmployee.isPresent()) {
            List<String> dNames = new ArrayList<>();
            for(int dReport : fEmployee.get().directReports){
                dNames.add(getEmployeeName(dReport));
                fEmployee.get().directReportsNames = dNames.toArray(new String[0]);
            }
        }
        return fEmployee;
    }

    public String getEmployeeName(int id){
        Optional<Employee> fEmployee = employees.stream()
                .filter(employee -> employee.id == id)
                .findFirst();
        return fEmployee.map(employee -> employee.name).orElse(null);
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

        // Create an ObjectMapper instance to handle the conversion to JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Write the list to the JSON file
            objectMapper.writeValue(file, employees);
            System.out.println("Employees have been written to json/data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
