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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table (name = "tb_overtime")
public class Overtime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String note;
    
    @Column(nullable = false)
    private String start_overtime;
    
    @Column(nullable = false)
    private String end_overtime;
    
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    
    @JsonIgnore
    @OneToMany(mappedBy = "overtime", cascade = CascadeType.ALL)
    private List<HistoryOvertime> historyOvertime;
    
    @ManyToOne
    @JoinColumn (name = "employee")
    private Employee employee;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "manager")
    private Employee manager;
    
    @ManyToOne
    @JoinColumn (name = "project")
    private Project project;
}
