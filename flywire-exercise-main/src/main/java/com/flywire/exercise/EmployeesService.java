package com.flywire.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class EmployeesService {

    private Employee[] employees;

    public EmployeesService(){
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/data.json")) {
            if (inputStream != null) {
                employees = objectMapper.readValue(inputStream, Employee[].class);
            } else {
                System.err.println("Could not find 'data.json' in resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee[] getAll(){

        return employees;
    }
}
