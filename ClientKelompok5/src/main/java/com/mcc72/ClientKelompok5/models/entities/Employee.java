package com.mcc72.ClientKelompok5.models.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author HP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private UserEntity user;
    private List<Employee> managers;
    private Employee manager;
    private List<Project> employeeProject;
    private StockLeave stockLeave;
    private List<Overtime> overtimes;
    private List<Permission> permissions;
    private List<Project> projects;
}
