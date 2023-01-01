/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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
@Table (name = "tb_project")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "manager")
    private Employee manager;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Boolean status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_employee_project",
            joinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "employee_id", referencedColumnName = "id"))
    private List<Employee> employeeProject;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @Column(nullable = true)
    private List<Overtime> overtimes;



}
