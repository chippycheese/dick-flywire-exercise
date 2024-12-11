package com.flywire.exercise;

public class FindEmployeeResponse {

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


    public FindEmployeeResponse(Employee newEmployeeRequest){
        this.active = true;
        directReports = newEmployeeRequest.directReports;
        hireDate = newEmployeeRequest.hireDate;
        name = newEmployeeRequest.name;
        position = newEmployeeRequest.position;
        id = newEmployeeRequest.id;
    }
}
