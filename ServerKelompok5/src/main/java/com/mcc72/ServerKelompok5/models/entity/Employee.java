/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hendi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "tb_employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", length = 6)
    private int id;
    
    @Column(length = 20, nullable = false)
    private String first_name;
    
    @Column (length = 25)
    private String last_name;
    
    @Column (length = 25, unique = false)
    private String email;
    
    @Column (length = 20, nullable = false)
    private String phone_number;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserEntity user;
    
    @OneToMany(mappedBy = "manager")
    private List<Employee> managers; 
    
    @ManyToOne
    @JoinColumn (name = "manager")
    private Employee manager;
    
    @ManyToMany
    @JoinTable(
            name = "tb_employee_project",
            joinColumns = @JoinColumn(
                    name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"))
    private List<Project> employeeProject;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StockLeave stockLeave;
    
    @OneToMany(mappedBy = "employee")
    private List<Overtime> overtimes;
    
    @OneToMany(mappedBy = "employee")
    private List<Permission> permissions;
    
    @OneToMany(mappedBy = "manager")
    private List<Project> projects;
}
