/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Hendi
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 6)
    private int id;

    @Column(length = 20, nullable = false)
    private String first_name;

    @Column(length = 25)
    private String last_name;

    @Column(length = 25, unique = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone_number;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Employee> managers;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager")
    private Employee manager;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tb_employee_project",
            joinColumns = @JoinColumn(
                    name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"))
    private List<Project> employeeProject;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StockLeave stockLeave;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Overtime> overtimes;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Permission> permissions;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<Project> projects;
}
