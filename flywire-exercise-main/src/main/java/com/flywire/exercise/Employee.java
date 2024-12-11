package com.flywire.exercise;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {

    public boolean active;
    public int[] directReports;
    public String hireDate;
    public int id;
    public String name;
    public String position;
    public String[] directReportsNames;

    public static String getLastName(Employee person) {
        String[] nameParts = person.name.split(" ");
        return nameParts[nameParts.length - 1]; // Get the last part of the name
    }
}
