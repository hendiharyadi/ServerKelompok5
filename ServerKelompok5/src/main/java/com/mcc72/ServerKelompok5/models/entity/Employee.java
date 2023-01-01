/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc72.ServerKelompok5.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private Integer id;

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
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Employee> managers;

    @ManyToOne
    @JoinColumn(name = "manager")
    private Employee manager;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "employeeProject")
    private List<Project> employeeProject = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StockLeave stockLeave;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Overtime> overtimes;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Permission> permissions;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<HistoryPermission> historyPermissions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade=CascadeType.ALL)
    private List<HistoryOvertime> historyOvertimes = new ArrayList<>();
}
