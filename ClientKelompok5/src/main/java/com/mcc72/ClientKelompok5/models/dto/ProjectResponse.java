package com.mcc72.ClientKelompok5.models.dto;

import com.mcc72.ClientKelompok5.models.entities.Employee;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {
    private int id;

    private List<Employee> members;
    private String name;
    private String start_project;
    private String end_project;

}
