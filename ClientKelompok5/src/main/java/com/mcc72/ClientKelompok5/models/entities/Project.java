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
public class Project {
    
    private Integer id;
    private Employee manager;
    private String name;
    private Boolean status;
    private List<Employee> employeeProject;
    private List<Overtime> overtimes;
}
